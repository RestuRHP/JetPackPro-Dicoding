package net.learn.jetpack.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.learn.jetpack.data.repository.MovieRepository

class MainViewModel(private val movieSet: MovieRepository) : ViewModel() {
    private val mViewState = MutableLiveData<MainViewState>().apply {
        value = MainViewState(loading = true)
    }
    val viewState: LiveData<MainViewState>
        get() = mViewState

    init {
        getSets()
    }

    fun getSets() = viewModelScope.launch {
        try {
            val data = movieSet.getSets()
            mViewState.value = mViewState.value?.copy(loading = false, error = null, data = data)
        } catch (ex: Exception) {
            mViewState.value = mViewState.value?.copy(loading = false, error = ex, data = null)
        }
    }
}