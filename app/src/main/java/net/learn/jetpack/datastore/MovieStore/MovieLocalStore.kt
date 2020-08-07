package net.learn.jetpack.datastore.MovieStore

import net.learn.submission4mvvm.model.movies.Movie

class MovieLocalStore : MovieDataStore {
    private var caches = mutableListOf<Movie>()

    override suspend fun getSets(page: Int?): MutableList<Movie>? =
        if (caches.isNotEmpty()) caches else null

    override suspend fun addAll(sets: MutableList<Movie>?) {
        sets?.let { caches = it }
    }
}