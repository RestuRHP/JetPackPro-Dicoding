package net.learn.jetpack.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import net.learn.jetpack.model.movies.Movie
import net.learn.jetpack.repository.movie.MovieRepository

class MovieViewModel(private val movieSet: MovieRepository) : ViewModel() {

    var currentItem: Flow<PagingData<Movie>>? = null

    private fun getMovie(): Flow<PagingData<Movie>> {
        val lastResult = currentItem
        if (lastResult != null) {
            return lastResult
        }
        val newResult: Flow<PagingData<Movie>> = movieSet.getMovie().cachedIn(viewModelScope)
        currentItem = newResult
        return newResult
    }

    init {
        getMovie()
    }

}