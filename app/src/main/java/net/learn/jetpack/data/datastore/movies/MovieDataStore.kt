package net.learn.jetpack.data.datastore.movies

import net.learn.jetpack.data.model.movies.Movie

interface MovieDataStore {
    suspend fun getDiscoveryMovie(page: Int): MutableList<Movie>?
    suspend fun addAll(sets: MutableList<Movie>?)
}