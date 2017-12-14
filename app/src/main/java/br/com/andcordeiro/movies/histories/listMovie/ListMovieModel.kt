package br.com.andcordeiro.movies.histories.listMovie

import android.content.Context
import br.com.andcordeiro.movies.entities.PopularityMovieEntity
import br.com.andcordeiro.movies.system.Consts
import br.com.andcordeiro.movies.system.mvp.ModelAbstract
import br.com.andcordeiro.movies.system.retrofit.Api
import br.com.andcordeiro.movies.system.retrofit.ApiProvider
import br.com.andcordeiro.movies.system.retrofit.callback.getpopularitymovies.GetPopularityMoviesCallback
import br.com.andcordeiro.movies.system.util.RxUtils
import retrofit2.Response
import rx.Observable
import rx.schedulers.Schedulers
import java.lang.Exception
import java.util.*

class ListMovieModel(private var context: Context) : ModelAbstract(), ListMovieContract.Model {

    private val api: Api = ApiProvider(context).api
    var sharedPreferences = context.getSharedPreferences(Consts.SharedPreferences.NAME, Context.MODE_PRIVATE)

    override fun getContext(): Context {
        return context
    }

    override fun popularityMoviesAsync(numberPage: Int): Observable<List<PopularityMovieEntity>> {
        return RxUtils.makeObservable({ popularityMovies(numberPage) }).subscribeOn(Schedulers.computation())
    }

    override fun popularityMovies(numberPage: Int): List<PopularityMovieEntity> {
        var popularityMovieEntities = ArrayList<PopularityMovieEntity>()
        try {
            val callback: Response<GetPopularityMoviesCallback> =
                    api.getPopularityMovies(Consts.Hash.API_KEY, numberPage, Consts.Language.PT_BR).execute()
            if (callback.isSuccessful) {
                val body: GetPopularityMoviesCallback = callback.body()
                popularityMovieEntities = body.popularityMovieEntities as ArrayList<PopularityMovieEntity>
                val editor = sharedPreferences.edit()
                editor.putInt(Consts.SharedPreferences.INT_PAGE, body.numberPage)
                editor.putInt(Consts.SharedPreferences.INT_TOTAL_PAGE, body.totalPages)
                editor.apply()
            } else {
                lastError = callback.message()
            }
        } catch (e: Exception) {
            lastException = e
            lastError = e.message
            e.printStackTrace()
        }
        return popularityMovieEntities
    }

    override fun searchPopularityMovies(numberPage: Int, query: String): List<PopularityMovieEntity> {
        var popularityMovieEntities = ArrayList<PopularityMovieEntity>()
        try {
            val callback: Response<GetPopularityMoviesCallback> =
                    api.getSearchMovies(Consts.Hash.API_KEY, numberPage, Consts.Language.PT_BR, query).execute()
            if (callback.isSuccessful) {
                val body: GetPopularityMoviesCallback = callback.body()
                popularityMovieEntities = body.popularityMovieEntities as ArrayList<PopularityMovieEntity>
                val editor = sharedPreferences.edit()
                editor.putInt(Consts.SharedPreferences.INT_SEARCH_PAGE, body.numberPage)
                editor.putInt(Consts.SharedPreferences.INT_SEARCH_TOTAL_PAGE, body.totalPages)
                editor.apply()
            } else {
                lastError = callback.message()
            }
        } catch (e: Exception) {
            lastException = e
            lastError = e.message
            e.printStackTrace()
        }
        return popularityMovieEntities
    }

    override fun searchPopularityMoviesAsync(numberPage: Int, query: String): Observable<List<PopularityMovieEntity>> {
        return RxUtils.makeObservable({ searchPopularityMovies(numberPage, query) }).subscribeOn(Schedulers.computation())
    }
}