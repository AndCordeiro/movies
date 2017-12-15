package br.com.andcordeiro.movies.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class GenreEntity : Serializable{

    @SerializedName("id") var id: Int? = null
    @SerializedName("name") var name: String? = null

    override fun toString(): String {
        return "GenreEntity(id=$id, name=$name)"
    }


    companion object {
        fun getGenresName(genres: List<GenreEntity>?): String{
            val nameGenres = StringBuilder()
            genres?.forEach { genre: GenreEntity? ->
                nameGenres.append(genre?.name).append(", ")
            }
            if (nameGenres.isNotEmpty()) {
                nameGenres.deleteCharAt(nameGenres.length - 2)
            }
            return nameGenres.toString()
        }
    }
}