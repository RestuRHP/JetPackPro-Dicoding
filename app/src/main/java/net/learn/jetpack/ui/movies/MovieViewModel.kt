package net.learn.jetpack.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.learn.jetpack.repository.MovieRepository

class MovieViewModel(private val movieSet: MovieRepository) : ViewModel() {
    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val mViewState = MutableLiveData<MovieViewState>().apply {
        value = MovieViewState(loading = true)
    }
    val viewState: LiveData<MovieViewState>
        get() = mViewState

    init {
        getSets(null)
        paginationSet(null)
    }

    fun getSets(page: Int?) = viewModelScope.launch {
        try {
            val data = movieSet.getSets(page)
            mViewState.value = mViewState.value?.copy(loading = false, error = null, data = data)
        } catch (ex: Exception) {
            mViewState.value = mViewState.value?.copy(loading = false, error = ex, data = null)
        }
    }

    fun paginationSet(page: Int?) = viewModelScope.launch {
        try {
            val data = movieSet.paginationSets(page)
            mViewState.value = mViewState.value?.copy(loading = false, error = null, data = data)
        } catch (ex: Exception) {
            mViewState.value = mViewState.value?.copy(loading = false, error = ex, data = null)
        }
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {

        }
    }
}