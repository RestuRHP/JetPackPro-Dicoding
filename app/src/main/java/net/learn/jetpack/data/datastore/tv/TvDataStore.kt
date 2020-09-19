package net.learn.jetpack.data.datastore.tv

import net.learn.jetpack.data.model.tv.TvShow

interface TvDataStore {
    suspend fun getDiscoveryTv(page: Int): MutableList<TvShow>?
    suspend fun addAll(sets: MutableList<TvShow>?)
}