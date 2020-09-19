package net.learn.jetpack.ui.favorite.tv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import net.learn.jetpack.data.model.tv.TvShow
import net.learn.jetpack.data.repository.favorite.FavoriteTvRepositoryImpl

interface FavoriteTvViewModel {
    suspend fun getFavoriteTv()
}

class FavoriteTvViewModelImpl(private val repository: FavoriteTvRepositoryImpl) :
    FavoriteTvViewModel,
    ViewModel() {
    private val _state = MutableLiveData<FavoriteTvState>()
    val state: LiveData<FavoriteTvState>
        get() = _state

    override suspend fun getFavoriteTv() {
        _state.value = FavoriteTvState.ShowLoading
        try {
            val data = repository.getFavoriteTv()
            if (data == null) {
                _state.value = FavoriteTvState.HideLoading
                _state.value = FavoriteTvState.LoadEmptyScreen
            } else {
                _state.value = FavoriteTvState.HideLoading
                _state.value = FavoriteTvState.LoadFavoriteMovie(data)
            }
        } catch (ex: Exception) {
            _state.value = FavoriteTvState.HideLoading
            _state.value = FavoriteTvState.LoadScreenError
        }
    }

    suspend fun tvShow() = repository.getFavoriteTv()
}

sealed class FavoriteTvState {
    object ShowLoading : FavoriteTvState()
    object HideLoading : FavoriteTvState()
    object LoadScreenError : FavoriteTvState()
    object LoadEmptyScreen : FavoriteTvState()

    data class LoadFavoriteMovie(val data: LiveData<PagedList<TvShow>>) : FavoriteTvState()
}