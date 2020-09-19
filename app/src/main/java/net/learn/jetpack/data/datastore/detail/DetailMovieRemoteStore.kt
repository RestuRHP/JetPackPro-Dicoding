package net.learn.jetpack.data.datastore.detail

import net.learn.jetpack.data.model.movies.Movie
import net.learn.jetpack.data.network.Api
import net.learn.jetpack.utils.EspressoIdlingResource

class DetailMovieRemoteStore(private val service: Api) : BaseDetailRemoteDataStore<Movie> {
    override suspend fun getSimilar(movieId: Int): MutableList<Movie>? {
        EspressoIdlingResource.increment()
        val response = service.getSimilarMovie(movieId)
        if (response.isSuccessful) {
            EspressoIdlingResource.decrement()
            return response.body()?.results
        }
        throw Exception("${response.code()}")
    }
}