package br.com.andcordeiro.movies.histories.detailMovie

import android.content.Context
import br.com.andcordeiro.movies.entities.MovieEntity
import br.com.andcordeiro.movies.system.Consts
import br.com.andcordeiro.movies.system.mvp.ModelAbstract
import br.com.andcordeiro.movies.system.retrofit.Api
import br.com.andcordeiro.movies.system.retrofit.ApiProvider
import br.com.andcordeiro.movies.system.util.RxUtils
import retrofit2.Response
import rx.Observable
import rx.schedulers.Schedulers
import java.lang.Exception

class DetailMovieModelImpl(private var context: Context) : ModelAbstract(), DetailMovieModel {

    private val api: Api = ApiProvider(context).api

    override fun getContext(): Context {
        return context
    }

    override fun movieByIdAsync(id: Int): Observable<MovieEntity> {
        return RxUtils.makeObservable({ movieById(id) }).subscribeOn(Schedulers.computation())
    }

    override fun movieById(id: Int): MovieEntity {
        var movieEntity = MovieEntity()
        try {
            val callback: Response<MovieEntity> =
                    api.getMovie(id, Consts.Hash.API_KEY, Consts.Language.PT_BR).execute()
            if (callback.isSuccessful) {
                movieEntity = callback.body()
            } else {
                lastError = callback.message()
            }
        } catch (e: Exception) {
            lastException = e
            lastError = e.message
            e.printStackTrace()
        }
        return movieEntity
    }

}