package net.learn.jetpack.ui.tv.store

import net.learn.jetpack.model.tv.TvShow
import net.learn.jetpack.utils.service.Api

class TvRemoteStore(private val api: Api) : TvDataStore {
    override suspend fun getSets(): MutableList<TvShow>? {
        val response = api.getTv()
        if (response.isSuccessful)
            return response.body()?.results

        throw Exception("Terjadi kesalahan saat melakukan request data, status error ${response.code()}")
    }

    override suspend fun addAll(sets: MutableList<TvShow>?) {

    }

}