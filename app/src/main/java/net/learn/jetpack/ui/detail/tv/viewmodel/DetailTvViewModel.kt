package net.learn.jetpack.ui.detail.tv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.learn.jetpack.data.repository.detail.DetailTvRepository
import net.learn.jetpack.ui.BaseViewState


interface DetailTvViewModelContract {
    suspend fun getSimilarTv(tvId: Int)
    suspend fun setTvToFavorite(tvId: Int)
    suspend fun removeTvFromFavorite(tvId: Int)
    suspend fun isFavorite(tvId: Int)
}

class DetailTvViewModel(private val repository: DetailTvRepository) : ViewModel(),
    DetailTvViewModelContract {

    private val _state = MutableLiveData<BaseViewState>()
    val state: LiveData<BaseViewState>
        get() = _state

    override suspend fun getSimilarTv(tvId: Int) {
        _state.value = BaseViewState.ShowLoading
        try {
            val data = repository.getSimilarTv(tvId)
            _state.value = BaseViewState.HideLoading
            _state.postValue(BaseViewState.LoadSimilarTvSuccess(data))
        } catch (ex: Exception) {
            _state.value = BaseViewState.HideLoading
            _state.value = BaseViewState.LoadScreenError
        }
    }

    override suspend fun setTvToFavorite(tvId: Int) {
        try {
            repository.setTvToFavorite(tvId)
            _state.value = BaseViewState.SuccessAddFavorite
        } catch (ex: Exception) {
            _state.value = BaseViewState.FailedAddFavorite
        }
    }

    override suspend fun removeTvFromFavorite(tvId: Int) {
        try {
            repository.removeTvFromFavorite(tvId)
            _state.value = BaseViewState.SuccessRemoveFavorite
        } catch (ex: Exception) {
            _state.value = BaseViewState.FailedRemoveFavorite
        }
    }

    override suspend fun isFavorite(tvId: Int) {
        val data = repository.isFavorite(tvId)
        if (data == null || data.size == 0) {
            _state.postValue(BaseViewState.IsFavoriteTheater(false))
        } else {
            _state.postValue(BaseViewState.IsFavoriteTheater(true))
        }
    }
}