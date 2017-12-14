package br.com.andcordeiro.movies.histories.detailMovie

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import br.com.andcordeiro.movies.R
import br.com.andcordeiro.movies.baseActivities.BaseActivity
import br.com.andcordeiro.movies.entities.GenreEntity
import br.com.andcordeiro.movies.entities.MovieEntity
import br.com.andcordeiro.movies.system.Consts
import br.com.andcordeiro.movies.system.extensions.find
import br.com.andcordeiro.movies.system.extensions.hide
import br.com.andcordeiro.movies.system.extensions.loadImage
import br.com.andcordeiro.movies.system.extensions.show
import br.com.andcordeiro.movies.system.mvp.PresenterHolder
import br.com.andcordeiro.movies.system.util.DeviceUtils

class DetailMovieActivity : BaseActivity(), DetailMovieContract.View {

    private val toolbar by lazy { find<Toolbar>(R.id.toolbar) }
    private val ivDetailMovie by lazy { find<ImageView>(R.id.iv_detail_movie) }
    private val collapsingToolbar by lazy { find<CollapsingToolbarLayout>(R.id.collapsing_toolbar) }
    private val tvOriginalTitle by lazy { find<TextView>(R.id.tv_original_title) }
    private val tvRuntime by lazy { find<TextView>(R.id.tv_runtime) }
    private val tvOriginalLanguage by lazy { find<TextView>(R.id.tv_original_language) }
    private val tvGenre by lazy { find<TextView>(R.id.tv_genre) }
    private val tvPopularity by lazy { find<TextView>(R.id.tv_popularity) }
    private val tvReleaseDate by lazy { find<TextView>(R.id.tv_release_date) }
    private val tvVoteAverage by lazy { find<TextView>(R.id.tv_vote_average) }
    private val tvVoteCount by lazy { find<TextView>(R.id.tv_vote_count) }
    private val tvBudget by lazy { find<TextView>(R.id.tv_budget) }
    private val tvRevenue by lazy { find<TextView>(R.id.tv_revenue) }
    private val tvOverview by lazy { find<TextView>(R.id.tv_overview) }
    private val pb by lazy { find<ProgressBar>(R.id.pb) }

    private var detailPresenter: DetailMoviePresenter? = null
    private var id: Int? = null

    override fun getLayout(): Int = R.layout.activity_detail_movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        setSupportActionBar(toolbar)
        detailPresenter = createPresenter()
        detailPresenter?.create()
        val intent = intent
        id = intent.getIntExtra(Consts.Intent.ID, 70)
        initViews()
    }

    private fun initViews() {
        pb.show()
        if (DeviceUtils.isConnected(this)) {
            detailPresenter!!.movieById(this.id!!)
        } else {
            pb.hide()
            showAlert(getString(R.string.title),
                    getString(R.string.without_internet),
                    getString(R.string.button_ok),
                    { _, _ ->
                        finish()
                    },
                    false)
        }
    }

    private fun createPresenter(): DetailMoviePresenter {
        if (detailPresenter == null) {
            detailPresenter = DetailMoviePresenter(this)
            PresenterHolder.getInstance().putPresenter(DetailMoviePresenter::class.java, detailPresenter)
        } else {
            detailPresenter?.setView(this)
        }
        return detailPresenter as DetailMoviePresenter
    }

    override fun onDestroy() {
        super.onDestroy()
        detailPresenter?.destroy()
    }

    override fun movie(movieEntity: MovieEntity) {
        collapsingToolbar.title = movieEntity.title
        ivDetailMovie.loadImage(movieEntity.posterPath)
        tvOriginalTitle.text = movieEntity.originalTitle
        tvRuntime.text = movieEntity.runtime.toString()
        tvOriginalLanguage.text = movieEntity.originalLanguage.toString()
        tvGenre.text = getNameGenres(movieEntity.genres)
        tvPopularity.text = movieEntity.popularity.toString()
        tvReleaseDate.text = movieEntity.releaseDate
        tvVoteAverage.text = movieEntity.voteAverage.toString()
        tvVoteCount.text = movieEntity.voteCount.toString()
        tvBudget.text = movieEntity.budget.toString()
        tvRevenue.text = movieEntity.revenue.toString()
        tvOverview.text = movieEntity.overview
        pb.hide()
    }

    fun getNameGenres(genres: List<GenreEntity>?): String {
        val nameGenres = StringBuilder()
        genres?.forEach { genre: GenreEntity? ->
            nameGenres.append(genre?.name).append(", ")
        }
        if (nameGenres.isNotEmpty()) {
            nameGenres.deleteCharAt(nameGenres.length - 2)
        }
        return nameGenres.toString()
    }
}