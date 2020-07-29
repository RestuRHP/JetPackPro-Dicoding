package net.learn.jetpack.repository

import net.learn.jetpack.ui.movies.store.MovieDataStore
import net.learn.submission4mvvm.model.movies.Movie

class MovieRepository private constructor() : BaseRepository<MovieDataStore>() {
    suspend fun getSets(): MutableList<Movie>? {
        val cache = localStore?.getSets()
        if (cache != null) return cache
        val response = remoteStore?.getSets()
        localStore?.addAll(response)
        return response
    }

    companion object {
        val instance by lazy { MovieRepository() }
    }
}