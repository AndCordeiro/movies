package br.com.andcordeiro.movies.histories.detailMovie

import rx.Subscription
import rx.android.schedulers.AndroidSchedulers

class DetailMoviePresenter(private var view : DetailMovieContract.View) : DetailMovieContract.Presenter {

    private var subscription: Subscription? = null
    private val model by lazy { DetailMovieModelImpl(view.context) }

    override fun create() {

    }

    override fun destroy() {
        if(subscription != null && !subscription!!.isUnsubscribed){
            subscription!!.unsubscribe()
        }
    }

    override fun setView(view: Any?) {
        this.view = view as DetailMovieContract.View
    }

    override fun movieById(id: Int) {
        subscription = model.movieByIdAsync(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({view.movie(it)})
    }

}
