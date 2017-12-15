package br.com.andcordeiro.movies.histories.detailMovie

import br.com.andcordeiro.movies.entities.MovieEntity

interface DetailMovieContract {

    interface View : br.com.andcordeiro.movies.system.mvp.View {

        fun movie(movieEntity: MovieEntity)

    }

    interface Presenter : br.com.andcordeiro.movies.system.mvp.Presenter{

        fun movieById(id: Int)

    }

}