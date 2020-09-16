package net.learn.jetpack.data.datastore.detail

import net.learn.jetpack.data.model.tv.TvShow
import net.learn.jetpack.data.network.Api
import net.learn.jetpack.utils.EspressoIdlingResource

class DetailTvRemoteStore(private val service: Api) : BaseDetailRemoteDataStore<TvShow> {
    override suspend fun getSimilar(movieId: Int): MutableList<TvShow>? {
        EspressoIdlingResource.increment()
        val response = service.getSimilarTvShow(movieId)
        if (response.isSuccessful) {
            EspressoIdlingResource.decrement()
            return response.body()?.results
        }
        throw Exception("${response.code()}")
    }
}