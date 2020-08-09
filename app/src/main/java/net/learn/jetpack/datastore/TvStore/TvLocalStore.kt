package net.learn.jetpack.datastore.TvStore

import net.learn.jetpack.model.tv.TvShow

class TvLocalStore : TvDataStore {
    private var caches = mutableListOf<TvShow>()

    override suspend fun getSets(page: Int): MutableList<TvShow>? =
        if (caches.isNotEmpty()) caches else null

    override suspend fun addAll(sets: MutableList<TvShow>?) {
        sets?.let { caches = it }
    }

}