package net.learn.jetpack.ui.movies

import net.learn.submission4mvvm.model.movies.Movie

data class MovieViewState(
    var loading: Boolean = false,
    var error: Exception? = null,
    var data: MutableList<Movie>? = null
)