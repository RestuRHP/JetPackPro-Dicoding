package net.learn.jetpack.repository

import net.learn.jetpack.datastore.MovieStore.MovieDataStore
import net.learn.submission4mvvm.model.movies.Movie

class MovieRepository private constructor() : BaseRepository<MovieDataStore>() {
    suspend fun getSets(page: Int?): MutableList<Movie>? {
        val cache = localStore?.getSets(page = page)
        if (cache != null) return cache
        val response = remoteStore?.getSets(page = page)
        localStore?.addAll(response)
        return response
    }

    suspend fun paginationSets(page: Int?): MutableList<Movie>? {
        val cache = localStore?.getSets(page)
        val response = remoteStore?.getSets(page)
        localStore?.addAll(response)
        return cache
    }

    companion object {
        val instance by lazy { MovieRepository() }
    }
}