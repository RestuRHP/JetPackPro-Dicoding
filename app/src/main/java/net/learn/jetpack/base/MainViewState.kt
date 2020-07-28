package net.learn.jetpack.base

import net.learn.submission4mvvm.model.movies.Movie

data class MainViewState(
    var loading: Boolean = false,
    var error: Exception? = null,
    var data: MutableList<Movie>? = null
)