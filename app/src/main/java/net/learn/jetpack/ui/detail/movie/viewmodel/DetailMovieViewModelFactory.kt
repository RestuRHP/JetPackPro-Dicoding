package net.learn.jetpack.ui.detail.movie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.learn.jetpack.data.repository.detail.DetailMovieRepository

@Suppress("UNCHECKED_CAST")
class DetailMovieViewModelFactory(private val detailMovieRepository: DetailMovieRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        DetailMovieViewModel(detailMovieRepository) as T
}