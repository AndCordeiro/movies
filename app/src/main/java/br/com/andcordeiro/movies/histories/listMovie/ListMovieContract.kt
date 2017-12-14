package br.com.andcordeiro.movies.histories.listMovie

import br.com.andcordeiro.movies.entities.PopularityMovieEntity
import rx.Observable

interface ListMovieContract {

    interface View : br.com.andcordeiro.movies.system.mvp.View {

        fun popularityMovies(popularityMovies: List<PopularityMovieEntity>)

        fun updatePopularityMovies(popularityMovies: List<PopularityMovieEntity>)

        fun searchPopularityMovies(popularityMovies: List<PopularityMovieEntity>)

        fun searchUpdatePopularityMovies(popularityMovies: List<PopularityMovieEntity>)

    }

    interface Presenter : br.com.andcordeiro.movies.system.mvp.Presenter {

        fun loadPopularityMovies(numberPage: Int)

        fun loadPopulatityMoviesToUpdate(numberPage: Int)

        fun searchPopulatityMovies(numberPage: Int, query: String)

        fun searchPopulatityMoviesToUpdate(numberPage: Int, query: String)

    }

    interface Model : br.com.andcordeiro.movies.system.mvp.Model{

        fun popularityMovies(numberPage: Int): List<PopularityMovieEntity>

        fun popularityMoviesAsync(numberPage: Int): Observable<List<PopularityMovieEntity>>

        fun searchPopularityMovies(numberPage: Int, query: String): List<PopularityMovieEntity>

        fun searchPopularityMoviesAsync(numberPage: Int, query: String): Observable<List<PopularityMovieEntity>>

    }
}