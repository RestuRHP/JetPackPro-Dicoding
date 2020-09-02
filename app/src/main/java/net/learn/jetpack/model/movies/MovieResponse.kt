package net.learn.submission4.data.response


import com.google.gson.annotations.SerializedName
import net.learn.jetpack.model.movies.Movie

data class MovieResponse(
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("results")
    val results: List<Movie> = emptyList()
//    ,
//    @SerializedName("total_pages")
//    var totalPages: Int
//    ,
//    @SerializedName("total_results")
//    var totalResults: Int
)