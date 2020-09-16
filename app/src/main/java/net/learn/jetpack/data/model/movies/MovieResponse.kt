package net.learn.submission4.data.response


import com.google.gson.annotations.SerializedName
import net.learn.jetpack.data.model.movies.Movie

data class MovieResponse(
    @SerializedName("page")
    var page: Int = 1,
    @SerializedName("results")
    var results: MutableList<Movie>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)