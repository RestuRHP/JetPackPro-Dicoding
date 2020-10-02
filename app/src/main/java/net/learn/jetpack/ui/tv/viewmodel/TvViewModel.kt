package net.learn.jetpack.ui.tv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.learn.jetpack.data.repository.TvRepository
import net.learn.jetpack.ui.BaseViewState

class TvViewModel(private val tvRepository: TvRepository) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 1
    }

    private val _state = MutableLiveData<BaseViewState>()
    val state: LiveData<BaseViewState>?
        get() = _state

    init {
        getDiscoveryTv()
    }

    fun getDiscoveryTv() = viewModelScope.launch {
        _state.value = BaseViewState.ShowLoading
        try {
            val data = tvRepository.loadDiscoveryTv()
            _state.value = BaseViewState.HideLoading
            _state.postValue(BaseViewState.LoadTvSuccess(data))
        } catch (ex: Exception) {
            _state.value = BaseViewState.HideLoading
            _state.value = BaseViewState.Error
        }
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) =
        viewModelScope.launch {
            if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
                try {
                    tvRepository.paginationSets()
                    val data = tvRepository.getDiscoveryTvFromDB()
                    _state.postValue(BaseViewState.LoadTvSuccess(data))
                } catch (ex: Exception) {
                }
            }
        }
}
