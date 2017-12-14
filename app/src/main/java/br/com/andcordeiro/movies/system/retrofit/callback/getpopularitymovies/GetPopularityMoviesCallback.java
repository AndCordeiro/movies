package br.com.andcordeiro.movies.system.retrofit.callback.getpopularitymovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.andcordeiro.movies.R;
import br.com.andcordeiro.movies.entities.PopularityMovieEntity;
import br.com.andcordeiro.movies.system.Consts;
import br.com.andcordeiro.movies.system.retrofit.callback.Callback;

public class GetPopularityMoviesCallback extends Callback{



    @SerializedName("results")
    private List<PopularityMovieEntity> popularityMovieEntities;

    @SerializedName("page")
    private int numberPage;

    @SerializedName("total_pages")
    private int totalPages;

    public List<PopularityMovieEntity> getPopularityMovieEntities() {
        return popularityMovieEntities;
    }

    public void setPopularityMovieEntities(List<PopularityMovieEntity> popularityMovieEntities) {
        this.popularityMovieEntities = popularityMovieEntities;
    }

    public int getNumberPage() {
        return numberPage;
    }

    public void setNumberPage(int numberPage) {
        this.numberPage = numberPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
