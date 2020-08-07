package net.learn.jetpack.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.learn.jetpack.repository.TvRepository

class TvViewModel(private val tvSet: TvRepository) : ViewModel() {
    private val mViewState = MutableLiveData<TvViewState>().apply {
        value = TvViewState(loading = true)
    }
    val viewState: LiveData<TvViewState>
        get() = mViewState

    init {
        getSets(1)
    }

    fun getSets(page: Int) = viewModelScope.launch {
        try {
            val data = tvSet.getSets(page = page)
            mViewState.value = mViewState.value?.copy(loading = false, error = null, data = data)
        } catch (ex: Exception) {
            mViewState.value = mViewState.value?.copy(loading = false, error = ex, data = null)
        }
    }
}
