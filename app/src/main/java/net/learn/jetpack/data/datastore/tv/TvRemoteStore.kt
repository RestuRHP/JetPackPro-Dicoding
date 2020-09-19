package net.learn.jetpack.data.datastore.tv

import net.learn.jetpack.data.model.tv.TvShow
import net.learn.jetpack.data.network.Api
import net.learn.jetpack.utils.EspressoIdlingResource

class TvRemoteStore(private val api: Api) : TvDataStore {
    override suspend fun getDiscoveryTv(page: Int): MutableList<TvShow>? {
        EspressoIdlingResource.increment()
        val response = api.getDiscoveryTv(page = page)
        if (response.isSuccessful) {
            EspressoIdlingResource.decrement()
            return response.body()?.results
        }
        throw Exception("Terjadi kesalahan saat melakukan request data, status error ${response.code()}")
    }

    override suspend fun addAll(sets: MutableList<TvShow>?) {

    }

}