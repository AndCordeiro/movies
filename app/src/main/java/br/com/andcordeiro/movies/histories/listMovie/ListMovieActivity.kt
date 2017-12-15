package br.com.andcordeiro.movies.histories.listMovie

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.Menu
import android.widget.ImageView
import android.widget.ProgressBar
import br.com.andcordeiro.movies.R
import br.com.andcordeiro.movies.baseActivities.BaseActivity
import br.com.andcordeiro.movies.baseActivities.ToolbarActivity
import br.com.andcordeiro.movies.entities.PopularityMovieEntity
import br.com.andcordeiro.movies.histories.detailMovie.DetailMovieActivity
import br.com.andcordeiro.movies.histories.detailMovie.DetailMoviePresenter
import br.com.andcordeiro.movies.system.Consts
import br.com.andcordeiro.movies.system.extensions.find
import br.com.andcordeiro.movies.system.extensions.gone
import br.com.andcordeiro.movies.system.extensions.loadImage
import br.com.andcordeiro.movies.system.extensions.show
import br.com.andcordeiro.movies.system.mvp.PresenterHolder
import br.com.andcordeiro.movies.system.util.DeviceUtils

class ListMovieActivity : ToolbarActivity(), ListMovieContract.View, ListMovieAdapter.OnClickListener, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    private val recyclerView by lazy { find<RecyclerView>(R.id.rv_list) }
    private val swipeRefreshLayout by lazy { find<SwipeRefreshLayout>(R.id.swipe_refresh) }
    private val pb by lazy { find<ProgressBar>(R.id.pb_movies) }

    private var presenter: ListMoviePresenter? = null
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: ListMovieAdapter? = null
    private var loading = false
    private var visibleMinItens = 3
    private var isSearch = false
    private var query = ""

    override fun getMenuLayout() = R.menu.menu_movies

    override fun getContentLayout(): Int = R.layout.activity_list_movie

    override fun getToolbarTitle(): String = getString(R.string.list_movies_name)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
        presenter?.create()
        initViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }

    private fun initViews() {
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy < 0) {
                    return
                }
                val visibleItemCount = recyclerView?.getChildCount()
                val totalItemCount = layoutManager?.getItemCount()
                val firstVisibleItem = layoutManager?.findFirstVisibleItemPosition()
                if (!loading && (totalItemCount!! - visibleItemCount!!) <= (firstVisibleItem!! + visibleMinItens)) {
                    showRefresh()
                    loading = true
                    val sharedPreferences = context.getSharedPreferences(Consts.SharedPreferences.NAME, Context.MODE_PRIVATE)
                    val numberPage = if (!isSearch) (sharedPreferences.getInt(Consts.SharedPreferences.INT_PAGE, -1) + 1) else (sharedPreferences.getInt(Consts.SharedPreferences.INT_SEARCH_PAGE, -1) + 1)
                    val totalNumberPage = if(!isSearch) (sharedPreferences.getInt(Consts.SharedPreferences.INT_TOTAL_PAGE, -1)) else sharedPreferences.getInt(Consts.SharedPreferences.INT_SEARCH_TOTAL_PAGE, -1)
                    if (numberPage <= totalNumberPage) {
                        if (DeviceUtils.isConnected(context)) {
                            if(isSearch){
                                presenter?.searchPopulatityMoviesToUpdate(numberPage, query)
                            }else{
                                presenter?.loadPopulatityMoviesToUpdate(numberPage)
                            }
                        } else {
                            hideRefresh()
                            loading = false
                            if(!BaseActivity.isIsAlertShow()){
                                setIsAlertShow(true)
                                showAlert(getString(R.string.title),
                                        getString(R.string.without_internet),
                                        getString(R.string.button_ok),
                                        { _, _ ->
                                            setIsAlertShow(false)
                                        },
                                        false)
                            }
                        }
                    } else {
                        loading = false
                        hideRefresh()
                    }
                }
            }
        })
        swipeRefreshLayout.setOnRefreshListener(this)
        pb.show()
        loadMovies()
    }

    override fun popularityMovies(popularityMovies: List<PopularityMovieEntity>) {
        adapter = ListMovieAdapter(popularityMovies as MutableList<PopularityMovieEntity>, this, "teste")
        recyclerView.adapter = adapter
        pb.gone()
        hideRefresh()
    }

    override fun updatePopularityMovies(popularityMovies: List<PopularityMovieEntity>) {
        adapter?.updateItens(popularityMovies)
        adapter?.notifyDataSetChanged()
        hideRefresh()
        pb.gone()
        loading = false
    }

    override fun searchPopularityMovies(popularityMovies: List<PopularityMovieEntity>) {
        adapter = ListMovieAdapter(popularityMovies as MutableList<PopularityMovieEntity>, this, "teste")
        recyclerView.adapter = adapter
        hideRefresh()
    }

    override fun searchUpdatePopularityMovies(popularityMovies: List<PopularityMovieEntity>) {
        adapter?.updateItens(popularityMovies)
        adapter?.notifyDataSetChanged()
        hideRefresh()
        loading = false
    }

    override fun onItemClick(data: PopularityMovieEntity) {
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra(Consts.Intent.ID, data.id)
        startActivity(intent)
    }

    override fun onLongItemClick(data: PopularityMovieEntity) {
        val view = (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.alert_poster, null)
        val iv1 = view.find<ImageView>(R.id.iv_1)
        val iv2 = view.find<ImageView>(R.id.iv_2)
        iv1.loadImage(data.posterPath)
        iv2.loadImage(data.backdropPath)
        showAlert(view,
                getString(R.string.poster))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_item_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo((componentName)))
        searchView.queryHint = getString(R.string.search_name)
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        this.query = query!!
        loadSearchMovies(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText?.length!! < 1 && isSearch){
            loadMovies()
            isSearch = false
        }
        return false
    }

    override fun onRefresh() {
        showRefresh()
        loadMovies()
    }

    private fun loadMovies(){
        if (DeviceUtils.isConnected(this)) {
            isSearch = false
            presenter!!.loadPopularityMovies(1)
        } else {
            pb.gone()
            hideRefresh()
            showAlert(getString(R.string.title),
                    getString(R.string.list_movies_without_internet),
                    getString(R.string.button_ok),
                    getString(R.string.button_cancel),
                    { _, _ ->
                        loadMovies()
                    },{ _, _ ->
                if(adapter == null) {
                    finish()
                }
            },
                    false)
        }
    }

    private fun loadSearchMovies(query: String?){
        if (DeviceUtils.isConnected(this)) {
            isSearch = true
            presenter!!.searchPopulatityMovies(1, query!!)
        } else {
            hideRefresh()
            showAlert(getString(R.string.title),
                    getString(R.string.list_movies_without_internet),
                    getString(R.string.button_ok),
                    getString(R.string.button_cancel),
                    { _, _ ->
                        loadSearchMovies(query)
                    },null,
                    false)
        }
    }

    private fun createPresenter(): ListMoviePresenter {
        presenter = PresenterHolder.getInstance().getPresenter(ListMoviePresenter::class.java)
        if (presenter == null) {
            presenter = ListMoviePresenter(this)
            PresenterHolder.getInstance().putPresenter(DetailMoviePresenter::class.java, presenter)
        } else {
            presenter?.setView(this)
        }
        return presenter as ListMoviePresenter
    }

    private fun hideRefresh() {
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.setRefreshing(false)
        }
    }

    private fun showRefresh() {
        if (!swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.setRefreshing(true)
        }
    }
}
