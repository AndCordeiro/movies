package br.com.andcordeiro.movies.histories.listMovie

import br.com.andcordeiro.movies.entities.PopularityMovieEntity
import br.com.andcordeiro.movies.system.mvp.Model
import rx.Observable

interface ListMovieModel : Model {

    fun popularityMovies(numberPage: Int): List<PopularityMovieEntity>

    fun popularityMoviesAsync(numberPage: Int): Observable<List<PopularityMovieEntity>>

    fun searchPopularityMovies(numberPage: Int, query: String): List<PopularityMovieEntity>

    fun searchPopularityMoviesAsync(numberPage: Int, query: String): Observable<List<PopularityMovieEntity>>
}