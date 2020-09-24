package net.learn.jetpack.ui.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.learn.jetpack.data.repository.MovieRepository
import net.learn.jetpack.ui.BaseViewState

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 1
    }

    private val _state = MutableLiveData<BaseViewState>()
    val state: LiveData<BaseViewState>?
        get() = _state

    init {
        getDiscoveryMovie()
    }

    fun getDiscoveryMovie() = viewModelScope.launch {
        _state.value = BaseViewState.ShowLoading
        try {
            val data = movieRepository.loadDiscoveryMovie()
            _state.value = BaseViewState.HideLoading
            _state.postValue(BaseViewState.LoadMovieSuccess(data = data))
        } catch (ex: Exception) {
            _state.value = BaseViewState.HideLoading
            _state.value = BaseViewState.Error
        }
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) =
        viewModelScope.launch {
            if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
                try {
                    movieRepository.paginationSets()
                    val data = movieRepository.getDiscoveryMovieFromDB()
                    _state.postValue(BaseViewState.LoadMovieSuccess(data = data))
                } catch (ex: Exception) {
                }
            }
        }
}