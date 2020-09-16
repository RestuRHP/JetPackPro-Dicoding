package net.learn.jetpack.ui.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.learn.jetpack.data.repository.MovieRepository

@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory(private val movieRepository: MovieRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java))
            return MovieViewModel(movieRepository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}