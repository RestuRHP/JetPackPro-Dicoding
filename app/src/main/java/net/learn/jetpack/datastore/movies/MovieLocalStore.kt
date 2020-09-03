package net.learn.jetpack.datastore.movies

import androidx.paging.PagingSource
import net.learn.jetpack.model.movies.Movie

class MovieLocalStore : MovieDataStore {
    override suspend fun getSets(page: Int?): PagingSource<Int, Movie>? {
        TODO("Not yet implemented")
    }

    override suspend fun addAll(sets: MutableList<Movie>?) {
        TODO("Not yet implemented")
    }
//    private var caches = PagingSource<Int, Movie>?
//
//    override suspend fun getSets(page: Int?): PagingSource<Int, Movie>? =
//        if (caches.isNotEmpty()) caches else null
//
//    override suspend fun addAll(sets: MutableList<Movie>?) {
//        sets?.let { caches = it }
//    }
}