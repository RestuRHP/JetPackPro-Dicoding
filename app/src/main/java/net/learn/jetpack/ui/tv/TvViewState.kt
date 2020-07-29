package net.learn.jetpack.ui.tv

import net.learn.jetpack.model.tv.TvShow

data class TvViewState(
    var loading: Boolean = false,
    var error: Exception? = null,
    var data: MutableList<TvShow>? = null
)