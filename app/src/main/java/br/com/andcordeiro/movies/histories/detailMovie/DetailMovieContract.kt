package br.com.andcordeiro.movies.histories.detailMovie

import br.com.andcordeiro.movies.entities.MovieEntity
import rx.Observable

interface DetailMovieContract {

    interface View : br.com.andcordeiro.movies.system.mvp.View {

        fun movie(movieEntity: MovieEntity)

    }

    interface Presenter : br.com.andcordeiro.movies.system.mvp.Presenter{

        fun movieById(id: Int)

    }

    interface Model : br.com.andcordeiro.movies.system.mvp.Model{

        fun movieById(id: Int): MovieEntity

        fun movieByIdAsync(id: Int): Observable<MovieEntity>

    }

}