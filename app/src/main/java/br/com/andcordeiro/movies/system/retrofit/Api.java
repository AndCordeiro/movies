package br.com.andcordeiro.movies.system.retrofit;

import br.com.andcordeiro.movies.entities.MovieEntity;
import br.com.andcordeiro.movies.system.retrofit.callback.getpopularitymovies.GetPopularityMoviesCallback;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("movie/{movie_id}")
    Call<MovieEntity> getMovie(@Path("movie_id") Integer id, @Query("api_key") String key, @Query("language") String language);

    @GET("discover/movie")
    Call<GetPopularityMoviesCallback> getPopularityMovies(@Query("api_key") String key, @Query("page") int page, @Query("language") String language);

    @GET("search/movie")
    Call<GetPopularityMoviesCallback> getSearchMovies(@Query("api_key") String key, @Query("page") int page, @Query("language") String language, @Query("query") String query);

}
