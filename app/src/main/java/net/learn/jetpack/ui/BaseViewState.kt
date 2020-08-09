package net.learn.jetpack.ui

data class BaseViewState<T>(
    var loading: Boolean = false,
    var error: Exception? = null,
    var data: MutableList<T>? = null
)