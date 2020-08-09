package net.learn.jetpack.datastore.TvStore

import net.learn.jetpack.model.tv.TvShow

interface TvDataStore {
    suspend fun getSets(page: Int): MutableList<TvShow>?
    suspend fun addAll(sets: MutableList<TvShow>?)
}