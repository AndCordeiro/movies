package br.com.andcordeiro.movies.histories.listMovie

import rx.Subscription
import rx.android.schedulers.AndroidSchedulers

class ListMoviePresenter(private var view : ListMovieContract.View) : ListMovieContract.Presenter {

    private var subscriptionMovies: Subscription? = null
    private var subscriptionMoviesUpdate: Subscription? = null
    private var subscriptionSearchMovies: Subscription? = null
    private var subscriptionSearchMoviesUpdate: Subscription? = null
    private val model by lazy { ListMovieModelImpl(view.context) }

    override fun create() {

    }

    override fun destroy() {
        if(subscriptionMovies != null && !subscriptionMovies!!.isUnsubscribed){
            subscriptionMovies!!.unsubscribe()
        }
        if(subscriptionMoviesUpdate != null && !subscriptionMoviesUpdate!!.isUnsubscribed){
            subscriptionMoviesUpdate!!.unsubscribe()
        }
        if(subscriptionSearchMovies != null && !subscriptionSearchMovies!!.isUnsubscribed){
            subscriptionSearchMovies!!.unsubscribe()
        }
        if(subscriptionSearchMoviesUpdate != null && !subscriptionSearchMoviesUpdate!!.isUnsubscribed){
            subscriptionSearchMoviesUpdate!!.unsubscribe()
        }
    }

    override fun setView(view: Any?) {
        this.view = view as ListMovieContract.View
    }

    override fun loadPopularityMovies(numberPage: Int) {
        subscriptionMovies = model.popularityMoviesAsync(numberPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({view.popularityMovies(it)})
    }

    override fun loadPopulatityMoviesToUpdate(numberPage: Int) {
        subscriptionMoviesUpdate = model.popularityMoviesAsync(numberPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({view.updatePopularityMovies(it)})
    }

    override fun searchPopulatityMovies(numberPage: Int, query: String) {
        subscriptionSearchMovies = model.searchPopularityMoviesAsync(numberPage, query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({view.searchPopularityMovies(it)})
    }

    override fun searchPopulatityMoviesToUpdate(numberPage: Int, query: String) {
        subscriptionSearchMoviesUpdate = model.searchPopularityMoviesAsync(numberPage, query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({view.searchUpdatePopularityMovies(it)})
    }

}