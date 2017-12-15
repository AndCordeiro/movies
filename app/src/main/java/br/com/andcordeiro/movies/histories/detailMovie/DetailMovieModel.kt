package br.com.andcordeiro.movies.histories.detailMovie

import br.com.andcordeiro.movies.entities.MovieEntity
import br.com.andcordeiro.movies.system.mvp.Model
import rx.Observable

interface DetailMovieModel : Model {

    fun movieById(id: Int): MovieEntity

    fun movieByIdAsync(id: Int): Observable<MovieEntity>

}