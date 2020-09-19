package net.learn.jetpack.ui.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.learn.jetpack.data.model.movies.Movie
import net.learn.jetpack.data.repository.MovieRepository

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 1
    }

    private val _state = MutableLiveData<MovieState>()
    val state: LiveData<MovieState>?
        get() = _state

    init {
        getDiscoveryMovie()
    }

    fun getDiscoveryMovie() = viewModelScope.launch {
        _state.value = MovieState.ShowLoading
        try {
            val data = movieRepository.loadDiscoveryMovie()
            _state.value = MovieState.HideLoading
            _state.postValue(MovieState.LoadMovieSuccess(data = data))
        } catch (ex: Exception) {
            _state.value = MovieState.HideLoading
            _state.value = MovieState.Error
        }
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) =
        viewModelScope.launch {
            if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
                try {
                    movieRepository.paginationSets()
                    val data = movieRepository.getDiscoveryMovieFromDB()
                    _state.postValue(MovieState.LoadMovieSuccess(data = data))
                } catch (ex: Exception) {
                }
            }
        }
}

sealed class MovieState {
    object ShowLoading : MovieState()
    object HideLoading : MovieState()
    object Error : MovieState()

    data class LoadMovieSuccess(val data: MutableList<Movie>?) : MovieState()
}