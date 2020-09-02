package net.learn.jetpack.ui

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.learn.jetpack.model.movies.Movie

data class BaseViewState<T : Any>(
    var loading: Boolean = false,
    var loadmore: Boolean = false,
    var error: Exception? = null,
    var data: Flow<PagingData<Movie>>? = null
)