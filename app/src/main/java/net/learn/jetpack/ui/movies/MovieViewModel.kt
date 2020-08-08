package net.learn.jetpack.ui.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.learn.jetpack.repository.MovieRepository
import net.learn.jetpack.ui.BaseViewState
import net.learn.submission4mvvm.model.movies.Movie

class MovieViewModel(private val movieSet: MovieRepository) : ViewModel() {
    companion object {
        private const val VISIBLE_THRESHOLD = 1
    }

    private val mViewState = MutableLiveData<BaseViewState<Movie>>().apply {
        value = BaseViewState(loading = true)
    }
    val viewState: MutableLiveData<BaseViewState<Movie>>
        get() = mViewState

    init {
        getSets()
    }

    fun getSets() = viewModelScope.launch {
        try {
            val data = movieSet.getSets()
            mViewState.value = mViewState.value?.copy(loading = false, error = null, data = data)
        } catch (ex: Exception) {
            mViewState.value = mViewState.value?.copy(loading = false, error = ex, data = null)
        }
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) =
        viewModelScope.launch {
            if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
                try {
                    movieSet.paginationSets()
                    val data = movieSet.loadPage()
                    mViewState.value =
                        mViewState.value?.copy(loading = false, error = null, data = data)
                } catch (ex: Exception) {
                    mViewState.value =
                        mViewState.value?.copy(loading = false, error = ex, data = null)
                }
            }
        }
}