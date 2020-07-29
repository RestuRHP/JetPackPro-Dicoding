package net.learn.jetpack.data.store

import net.learn.submission4mvvm.model.movies.Movie

interface MovieDataStore {
    suspend fun getSets(): MutableList<Movie>?
    suspend fun addAll(sets: MutableList<Movie>?)
}