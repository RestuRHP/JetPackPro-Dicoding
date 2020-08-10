package net.learn.jetpack.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.learn.jetpack.model.tv.TvShow
import net.learn.jetpack.repository.TvRepository
import net.learn.jetpack.ui.BaseViewState

class TvViewModel(private val tvSet: TvRepository) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 1
    }

    private val mViewState = MutableLiveData<BaseViewState<TvShow>>().apply {
        value = BaseViewState(loading = true, error = null, data = null)
    }
    val viewState: LiveData<BaseViewState<TvShow>>
        get() = mViewState

    init {
        getSets()
    }

    fun getSets() = viewModelScope.launch {
        try {
            val data = tvSet.getSets()
            mViewState.value = mViewState.value?.copy(loading = false, error = null, data = data)
        } catch (ex: Exception) {
            mViewState.value = mViewState.value?.copy(loading = false, error = ex, data = null)
        }
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) =
        viewModelScope.launch {
            if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
                try {
                    tvSet.paginationSets()
                    val data = tvSet.loadPage()
                    mViewState.value =
                        mViewState.value?.copy(loading = false, error = null, data = data)
                } catch (ex: Exception) {
                    mViewState.value =
                        mViewState.value?.copy(loading = false, error = ex, data = null)
                }
            }
        }
}
