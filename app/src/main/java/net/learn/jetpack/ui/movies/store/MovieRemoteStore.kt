package net.learn.jetpack.ui.movies.store

import net.learn.jetpack.utils.service.Api
import net.learn.submission4mvvm.model.movies.Movie

class MovieRemoteStore(private val api: Api) : MovieDataStore {
    override suspend fun getSets(): MutableList<Movie>? {
        val response = api.getMovies()
        if (response.isSuccessful)
            return response.body()?.results

        throw Exception("Terjadi kesalahan saat melakukan request data, status error ${response.code()}")
    }

    override suspend fun addAll(sets: MutableList<Movie>?) {
    }

}