package net.learn.jetpack.ui.detail.tv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.learn.jetpack.data.repository.detail.DetailTvRepository

@Suppress("UNCHECKED_CAST")
class DetailTvViewModelFactory(private val detailTvRepository: DetailTvRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        DetailTvViewModel(detailTvRepository) as T
}