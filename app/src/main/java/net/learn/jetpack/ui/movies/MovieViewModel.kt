package net.learn.jetpack.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.learn.jetpack.data.repository.MovieRepository

class MovieViewModel(private val movieSet: MovieRepository) : ViewModel() {
    private val mViewState = MutableLiveData<MovieViewState>().apply {
        value = MovieViewState(loading = true)
    }
    val viewState: LiveData<MovieViewState>
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
}