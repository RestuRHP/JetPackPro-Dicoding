package net.learn.jetpack.ui.detail.tv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.learn.jetpack.data.model.tv.TvShow
import net.learn.jetpack.data.repository.detail.DetailTvRepository


interface DetailTvViewModelContract {
    suspend fun getSimilarTv(tvId: Int)
    suspend fun setTvToFavorite(tvId: Int)
    suspend fun removeTvFromFavorite(tvId: Int)
    suspend fun isFavorite(tvId: Int)
}

class DetailTvViewModel(private val repository: DetailTvRepository) : ViewModel(),
    DetailTvViewModelContract {

    private val _state = MutableLiveData<DetailTvState>()
    val state: LiveData<DetailTvState>
        get() = _state

    override suspend fun getSimilarTv(tvId: Int) {
        _state.value = DetailTvState.ShowLoading
        try {
            val data = repository.getSimilarTv(tvId)
            _state.value = DetailTvState.HideLoading
            _state.postValue(DetailTvState.LoadSimilarSuccess(data))
        } catch (ex: Exception) {
            _state.value = DetailTvState.HideLoading
            _state.value = DetailTvState.LoadScreenError
        }
    }

    override suspend fun setTvToFavorite(tvId: Int) {
        try {
            repository.setTvToFavorite(tvId)
            _state.value = DetailTvState.SuccessAddFavorite
        } catch (ex: Exception) {
            _state.value = DetailTvState.FailedAddFavorite
        }
    }

    override suspend fun removeTvFromFavorite(tvId: Int) {
        try {
            repository.removeTvFromFavorite(tvId)
            _state.value = DetailTvState.SuccessRemoveFavorite
        } catch (ex: Exception) {
            _state.value = DetailTvState.FailedRemoveFavorite
        }
    }

    override suspend fun isFavorite(tvId: Int) {
        val data = repository.isFavorite(tvId)
        if (data == null || data.size == 0) {
            _state.postValue(DetailTvState.IsFavoriteTheater(false))
        } else {
            _state.postValue(DetailTvState.IsFavoriteTheater(true))
        }
    }
}

sealed class DetailTvState {
    object ShowLoading : DetailTvState()
    object HideLoading : DetailTvState()
    object LoadScreenError : DetailTvState()
    object SuccessAddFavorite : DetailTvState()
    object SuccessRemoveFavorite : DetailTvState()
    object FailedRemoveFavorite : DetailTvState()
    object FailedAddFavorite : DetailTvState()

    data class IsFavoriteTheater(val status: Boolean) : DetailTvState()
    data class LoadSimilarSuccess(val data: MutableList<TvShow>?) : DetailTvState()
}