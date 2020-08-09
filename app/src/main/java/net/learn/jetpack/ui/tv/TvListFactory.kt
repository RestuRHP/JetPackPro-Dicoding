package net.learn.jetpack.ui.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.learn.jetpack.repository.TvRepository

@Suppress("UNCHECKED_CAST")
class TvListFactory(private val tvRepository: TvRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TvViewModel::class.java))
            return TvViewModel(tvRepository) as T
        throw IllegalArgumentException()
    }

}