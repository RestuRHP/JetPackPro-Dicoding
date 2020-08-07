package net.learn.jetpack.repository

import net.learn.jetpack.datastore.TvStore.TvDataStore
import net.learn.jetpack.model.tv.TvShow

class TvRepository private constructor() : BaseRepository<TvDataStore>() {
    suspend fun getSets(page: Int): MutableList<TvShow>? {
        val cache = localStore?.getSets(page = page)
        if (cache != null) return cache
        val response = remoteStore?.getSets(page = page)
        localStore?.addAll(response)
        return response
    }

    companion object {
        val instance by lazy { TvRepository() }
    }

}