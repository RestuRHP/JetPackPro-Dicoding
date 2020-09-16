package net.learn.jetpack.data.datastore.movies

import net.learn.jetpack.data.model.movies.Movie
import net.learn.jetpack.data.network.Api
import net.learn.jetpack.utils.EspressoIdlingResource

class MovieRemoteStore(private val api: Api) : MovieDataStore {
    override suspend fun getDiscoveryMovie(page: Int): MutableList<Movie>? {
        EspressoIdlingResource.increment()
        val response = api.getDiscoveryMovie(page = page)
        if (response.isSuccessful) {
            EspressoIdlingResource.decrement()
            return response.body()?.results
        }
        throw Exception("Terjadi kesalahan saat melakukan request data, status error ${response.code()}")
    }

    override suspend fun addAll(sets: MutableList<Movie>?) {
        TODO("Not yet implemented")
    }

}