package net.learn.jetpack.datastore.movies

import net.learn.jetpack.service.Api
import net.learn.submission4mvvm.model.movies.Movie

class MovieRemoteStore(private val api: Api) : MovieDataStore {
    override suspend fun getSets(page: Int?): MutableList<Movie>? {
        val response = api.getMovies(page = page)
        if (response.isSuccessful)
            return response.body()?.results

        throw Exception("Terjadi kesalahan saat melakukan request data, status error ${response.code()}")
    }

    override suspend fun addAll(sets: MutableList<Movie>?) {
    }

}