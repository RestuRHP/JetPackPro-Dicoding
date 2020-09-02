package net.learn.jetpack.datastore.movies

import androidx.paging.PagingSource
import net.learn.jetpack.model.movies.Movie

interface MovieDataStore {
    suspend fun getSets(page: Int?): PagingSource<Int, Movie>?
    suspend fun addAll(sets: MutableList<Movie>?)
}