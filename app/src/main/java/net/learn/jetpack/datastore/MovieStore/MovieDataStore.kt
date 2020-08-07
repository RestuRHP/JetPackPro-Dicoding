package net.learn.jetpack.datastore.MovieStore

import net.learn.submission4mvvm.model.movies.Movie

interface MovieDataStore {
    suspend fun getSets(page: Int?): MutableList<Movie>?
    suspend fun addAll(sets: MutableList<Movie>?)
}