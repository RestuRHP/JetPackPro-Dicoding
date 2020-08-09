package net.learn.jetpack.datastore.tv

import net.learn.jetpack.model.tv.TvShow
import net.learn.jetpack.service.Api

class TvRemoteStore(private val api: Api) : TvDataStore {
    override suspend fun getSets(page: Int): MutableList<TvShow>? {
        val response = api.getTv(page = page)
        if (response.isSuccessful)
            return response.body()?.results

        throw Exception("Terjadi kesalahan saat melakukan request data, status error ${response.code()}")
    }

    override suspend fun addAll(sets: MutableList<TvShow>?) {

    }

}