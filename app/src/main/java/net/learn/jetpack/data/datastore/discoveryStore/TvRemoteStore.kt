package net.learn.jetpack.data.datastore.discoveryStore

import net.learn.jetpack.data.model.tv.TvShow
import net.learn.jetpack.data.network.Api
import net.learn.jetpack.utils.EspressoIdlingResource

class TvRemoteStore(private val api: Api) : BaseRemoteDataStore<TvShow> {
    override suspend fun getDiscovery(page: Int): MutableList<TvShow>? {
        EspressoIdlingResource.increment()
        val response = api.getDiscoveryTv(page = page)
        if (response.isSuccessful) {
            EspressoIdlingResource.decrement()
            return response.body()?.results
        }
        throw Exception("${response.code()}")
    }
}