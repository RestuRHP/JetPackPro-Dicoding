package net.learn.jetpack.ui.tv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.learn.jetpack.data.model.tv.TvShow
import net.learn.jetpack.data.repository.TvRepository

class MovieViewModel(private val tvRepository: TvRepository) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 1
    }

    private val _state = MutableLiveData<TvState>()
    val state: LiveData<TvState>?
        get() = _state

    init {
        getDiscoveryTv()
    }

    fun getDiscoveryTv() = viewModelScope.launch {
        _state.value = TvState.ShowLoading
        try {
            val data = tvRepository.loadDiscoveryTv()
            _state.value = TvState.HideLoading
            _state.postValue(TvState.LoadTvSuccess(data))
        } catch (ex: Exception) {
            _state.value = TvState.HideLoading
            _state.value = TvState.Error
        }
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) =
        viewModelScope.launch {
            if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
                try {
                    tvRepository.paginationSets()
                    val data = tvRepository.getDiscoveryTvFromDB()
                    _state.postValue(TvState.LoadTvSuccess(data))
                } catch (ex: Exception) {
                }
            }
        }
}

sealed class TvState {
    object ShowLoading : TvState()
    object HideLoading : TvState()
    object Error : TvState()

    data class LoadTvSuccess(val data: MutableList<TvShow>?) : TvState()
}
