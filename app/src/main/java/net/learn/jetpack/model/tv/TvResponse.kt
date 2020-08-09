package net.learn.jetpack.model.tv

import com.google.gson.annotations.SerializedName

data class TvResponse(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var results: MutableList<TvShow>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)