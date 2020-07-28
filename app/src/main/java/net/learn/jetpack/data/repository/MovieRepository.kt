package net.learn.jetpack.data.repository

import net.learn.jetpack.data.store.MovieDataStore
import net.learn.submission4mvvm.model.movies.Movie

class MovieRepository private constructor() : BaseRepository<MovieDataStore>() {
    suspend fun getSets(): MutableList<Movie>? {
        val cache = localStore?.getSets()
        if (cache != null) return cache
        val respose = remoteStore?.getSets()
        localStore?.addAll(respose)
        return respose
    }

    companion object {
        val instance by lazy { MovieRepository }
    }
}