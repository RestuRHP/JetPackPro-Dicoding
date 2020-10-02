package net.learn.jetpack.ui.detail.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.learn.jetpack.data.repository.detail.DetailMovieRepository
import net.learn.jetpack.ui.BaseViewState

interface DetailMovieViewModelContract {
    suspend fun getSimilarMovie(movieId: Int)
    suspend fun setMovieToFavorite(movieId: Int)
    suspend fun isFavoriteMovie(movieId: Int)
    suspend fun removeMovieFromFavorite(movieId: Int)
}

class DetailMovieViewModel(private val repository: DetailMovieRepository) : ViewModel(),
    DetailMovieViewModelContract {

    private val _state = MutableLiveData<BaseViewState>()
    val state: LiveData<BaseViewState>
        get() = _state

    override suspend fun getSimilarMovie(movieId: Int) {
        _state.value = BaseViewState.ShowLoading
        try {
            val data = repository.getSimilarMovie(movieId)
            _state.value = BaseViewState.HideLoading
            _state.postValue(BaseViewState.LoadSimilarMovieSuccess(data))
        } catch (ex: Exception) {
            _state.value = BaseViewState.HideLoading
            _state.value = BaseViewState.LoadScreenError
        }
    }

    override suspend fun setMovieToFavorite(movieId: Int) {
        try {
            repository.setMovieToFavorite(movieId)
            _state.value = BaseViewState.SuccessAddFavorite
        } catch (ex: Exception) {
            _state.value = BaseViewState.FailedAddFavorite
        }
    }

    override suspend fun isFavoriteMovie(movieId: Int) {
        val data = repository.isFavoriteMovie(movieId)
        if (data == null || data.size == 0) {
            _state.postValue(BaseViewState.IsFavoriteTheater(false))
        } else {
            _state.postValue(BaseViewState.IsFavoriteTheater(true))
        }
    }

    override suspend fun removeMovieFromFavorite(movieId: Int) {
        try {
            repository.removeMovieFromFavorite(movieId)
            _state.value = BaseViewState.SuccessRemoveFavorite
        } catch (ex: Exception) {
            _state.value = BaseViewState.FailedRemoveFavorite
        }
    }

}