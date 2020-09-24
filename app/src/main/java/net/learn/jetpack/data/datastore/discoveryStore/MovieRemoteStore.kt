package net.learn.jetpack.data.datastore.discoveryStore

import net.learn.jetpack.data.model.movies.Movie
import net.learn.jetpack.data.network.Api
import net.learn.jetpack.utils.EspressoIdlingResource

class MovieRemoteStore(private val api: Api) : BaseRemoteDataStore<Movie> {
    override suspend fun getDiscovery(page: Int): MutableList<Movie>? {
        EspressoIdlingResource.increment()
        val response = api.getDiscoveryMovie(page = page)
        if (response.isSuccessful) {
            EspressoIdlingResource.decrement()
            return response.body()?.results
        }
        throw Exception("${response.code()}")
    }

}