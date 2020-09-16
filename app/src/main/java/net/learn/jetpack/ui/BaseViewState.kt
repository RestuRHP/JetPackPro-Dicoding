package net.learn.jetpack.ui


data class BaseViewState<T : Any>(
    var loading: Boolean = false,
    var loadmore: Boolean = false,
    var error: Exception? = null,
    var data: MutableList<T>? = null
//    var data: Flow<PagingData<Movie>>? = null
)