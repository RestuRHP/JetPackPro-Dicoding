package net.learn.jetpack.datastore.movies

import net.learn.jetpack.model.movies.Movie
import net.learn.jetpack.service.Api
import net.learn.jetpack.utils.EspressoIdlingResource

class MovieRemoteStore(private val api: Api) : MovieDataStore {
    override suspend fun getSets(page: Int?): MutableList<Movie>? {
        EspressoIdlingResource.increment()
        val response = api.getMovies(page = page)
        if (response.isSuccessful) {
            EspressoIdlingResource.decrement()
            return response.body()?.results
        }
        throw Exception("Terjadi kesalahan saat melakukan request data, status error ${response.code()}")
    }

    override suspend fun addAll(sets: MutableList<Movie>?) {
    }

}