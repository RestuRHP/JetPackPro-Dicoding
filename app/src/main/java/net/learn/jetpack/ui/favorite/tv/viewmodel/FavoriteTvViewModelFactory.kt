package net.learn.jetpack.ui.favorite.tv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.learn.jetpack.data.repository.favorite.FavoriteTvRepositoryImpl

@Suppress("UNCHECKED_CAST")
class FavoriteTvViewModelFactory(private val repository: FavoriteTvRepositoryImpl) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        FavoriteTvViewModelImpl(repository) as T
}