package net.learn.jetpack.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results")
    var movies: List<Movie>
)