package br.com.andcordeiro.movies.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class GenreEntity : Serializable{

    @SerializedName("id") var id: Int? = null
    @SerializedName("name") var name: String? = null

    override fun toString(): String {
        return "GenreEntity(id=$id, name=$name)"
    }
}