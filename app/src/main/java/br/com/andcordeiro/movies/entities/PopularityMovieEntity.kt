package br.com.andcordeiro.movies.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PopularityMovieEntity : Serializable {

    @SerializedName("id") var id: Int? = null
    @SerializedName("poster_path") var posterPath: String? = null
    @SerializedName("vote_average") var voteAverage: Double? = null
    @SerializedName("vote_count") var voteCount: Int? = null
    @SerializedName("popularity") var popularity: Double? = null
    @SerializedName("release_date") var releaseDate: String? = null
    @SerializedName("title") var title: String? = null
    @SerializedName("backdrop_path") var backdropPath: String? = null

    override fun toString(): String {
        return "PopularityMovieEntity(id=$id, posterPath=$posterPath, voteAverage=$voteAverage, " +
                "voteCount=$voteCount, popularity=$popularity, releaseDate=$releaseDate, " +
                "title=$title, backdropPath=$backdropPath)"
    }

}