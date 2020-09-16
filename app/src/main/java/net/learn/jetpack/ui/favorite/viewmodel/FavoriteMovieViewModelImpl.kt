package net.learn.jetpack.ui.favorite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import net.learn.jetpack.data.model.movies.Movie
import net.learn.jetpack.data.repository.favorite.FavoriteRepositoryImpl


interface FavoriteMovieViewModel {
    suspend fun getFavoriteMovie()
}

class FavoriteMovieViewModelImpl(private val repository: FavoriteRepositoryImpl) :
    FavoriteMovieViewModel, ViewModel() {

    private val _state = MutableLiveData<FavoriteMovieState>()
    val state: LiveData<FavoriteMovieState>
        get() = _state

    override suspend fun getFavoriteMovie() {
        _state.value = FavoriteMovieState.ShowLoading
        try {
            val data = repository.getFavoriteMovie()
            if (data == null) {
                _state.value = FavoriteMovieState.HideLoading
                _state.value = FavoriteMovieState.LoadEmptyScreen
            } else {
                _state.value = FavoriteMovieState.HideLoading
                _state.value = FavoriteMovieState.LoadFavoriteMovie(data)
            }
        } catch (ex: Exception) {
            _state.value = FavoriteMovieState.HideLoading
            _state.value = FavoriteMovieState.LoadScreenError
            throw ex
        }
    }
}

sealed class FavoriteMovieState {
    object ShowLoading : FavoriteMovieState()
    object HideLoading : FavoriteMovieState()
    object LoadScreenError : FavoriteMovieState()
    object LoadEmptyScreen : FavoriteMovieState()

    data class LoadFavoriteMovie(val data: LiveData<PagedList<Movie>>) : FavoriteMovieState()
}