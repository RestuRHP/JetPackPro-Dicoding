package net.learn.jetpack.repository

import net.learn.jetpack.model.tv.TvShow
import net.learn.jetpack.ui.tv.store.TvDataStore

class TvRepository private constructor() : BaseRepository<TvDataStore>() {
    suspend fun getSets(): MutableList<TvShow>? {
        val cache = localStore?.getSets()
        if (cache != null) return cache
        val response = remoteStore?.getSets()
        localStore?.addAll(response)
        return response
    }

    companion object {
        val instance by lazy { TvRepository() }
    }

}