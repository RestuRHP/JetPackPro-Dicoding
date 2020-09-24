package net.learn.jetpack.ui.tv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.learn.jetpack.data.repository.TvRepository

@Suppress("UNCHECKED_CAST")
class TvViewModelFactory(private val tvRepository: TvRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TvViewModel::class.java))
            return TvViewModel(tvRepository) as T
        throw IllegalArgumentException()
    }

}