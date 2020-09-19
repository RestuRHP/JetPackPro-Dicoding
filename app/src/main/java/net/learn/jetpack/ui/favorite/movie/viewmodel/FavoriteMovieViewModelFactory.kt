package net.learn.jetpack.ui.favorite.movie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.learn.jetpack.data.repository.favorite.FavoriteMovieRepositoryImpl

@Suppress("UNCHECKED_CAST")
class FavoriteMovieViewModelFactory(private val repository: FavoriteMovieRepositoryImpl) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        FavoriteMovieViewModelImpl(repository) as T
}