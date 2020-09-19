package net.learn.jetpack.ui.detail.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.learn.jetpack.data.model.movies.Movie
import net.learn.jetpack.data.repository.detail.DetailMovieRepository

interface DetailMovieViewModelContract {
    suspend fun getSimilarMovie(movieId: Int)
    suspend fun setMovieToFavorite(movieId: Int)
    suspend fun isFavoriteMovie(movieId: Int)
    suspend fun removeMovieFromFavorite(movieId: Int)
}

class DetailMovieViewModel(private val repository: DetailMovieRepository) : ViewModel(),
    DetailMovieViewModelContract {

    private val _state = MutableLiveData<DetailMovieState>()
    val state: LiveData<DetailMovieState>
        get() = _state

    override suspend fun getSimilarMovie(movieId: Int) {
        _state.value = DetailMovieState.ShowLoading
        try {
            val data = repository.getSimilarMovie(movieId)
            _state.value = DetailMovieState.HideLoading
            _state.postValue(DetailMovieState.LoadSimilarMovieSuccess(data))
        } catch (ex: Exception) {
            _state.value = DetailMovieState.HideLoading
            _state.value = DetailMovieState.LoadScreenError
        }
    }

    override suspend fun setMovieToFavorite(movieId: Int) {
        try {
            repository.setMovieToFavorite(movieId)
            _state.value = DetailMovieState.SuccessAddFavorite
        } catch (ex: Exception) {
            _state.value = DetailMovieState.FailedAddFavorite
        }
    }

    override suspend fun isFavoriteMovie(movieId: Int) {
        val data = repository.isFavoriteMovie(movieId)
        if (data == null || data.size == 0) {
            _state.postValue(DetailMovieState.IsFavoriteTheater(false))
        } else {
            _state.postValue(DetailMovieState.IsFavoriteTheater(true))
        }
    }

    override suspend fun removeMovieFromFavorite(movieId: Int) {
        try {
            repository.removeMovieFromFavorite(movieId)
            _state.value = DetailMovieState.SuccessRemoveFavorite
        } catch (ex: Exception) {
            _state.value = DetailMovieState.FailedRemoveFavorite
        }
    }

}

sealed class DetailMovieState {
    object ShowLoading : DetailMovieState()
    object HideLoading : DetailMovieState()
    object LoadScreenError : DetailMovieState()
    object SuccessAddFavorite : DetailMovieState()
    object SuccessRemoveFavorite : DetailMovieState()
    object FailedRemoveFavorite : DetailMovieState()
    object FailedAddFavorite : DetailMovieState()

    data class IsFavoriteTheater(val status: Boolean) : DetailMovieState()
    data class LoadSimilarMovieSuccess(val data: MutableList<Movie>?) : DetailMovieState()
}