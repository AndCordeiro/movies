package br.com.andcordeiro.movies.entities

import br.com.andcordeiro.movies.system.retrofit.callback.Callback
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MovieEntity : Serializable, Callback() {

    @SerializedName("id") var id: Int? = null
    @SerializedName("poster_path") var posterPath: String? = null
    @SerializedName("title") var title: String? = null
    @SerializedName("original_title") var originalTitle: String? = null
    @SerializedName("original_language") var originalLanguage: String? = null
    @SerializedName("popularity") var popularity: Double? = null
    @SerializedName("genres") var genres: List<GenreEntity>? = null
    @SerializedName("overview") var overview: String? = null
    @SerializedName("vote_average") var voteAverage: Double? = null
    @SerializedName("vote_count") var voteCount: Int? = null
    @SerializedName("release_date") var releaseDate: String? = null
    @SerializedName("runtime") var runtime: Int? = null
    @SerializedName("budget") var budget: Int? = null
    @SerializedName("revenue") var revenue: Int? = null

    override fun toString(): String {
        return "MovieEntity(id=$id, posterPath=$posterPath, title=$title, originalTitle=$originalTitle, originalLanguage=$originalLanguage, popularity=$popularity, genres=$genres, overview=$overview, voteAverage=$voteAverage, voteCount=$voteCount, releaseDate=$releaseDate, runtime=$runtime, budget=$budget, revenue=$revenue)"
    }
}