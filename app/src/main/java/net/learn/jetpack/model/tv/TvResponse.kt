package net.learn.jetpack.model.tv

import com.google.gson.annotations.SerializedName

data class TvResponse(
    @SerializedName("results")
    var results: MutableList<TvShow>
)