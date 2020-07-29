package net.learn.submission4.data.response


import com.google.gson.annotations.SerializedName
import net.learn.submission4mvvm.model.movies.Movie

data class MovieResponse(
    @SerializedName("results")
    var results: MutableList<Movie>
)