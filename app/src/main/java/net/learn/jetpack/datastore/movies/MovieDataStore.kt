package net.learn.jetpack.datastore.movies

import net.learn.jetpack.model.movies.Movie

interface MovieDataStore {
    suspend fun getSets(page: Int?): MutableList<Movie>?
    suspend fun addAll(sets: MutableList<Movie>?)
}