package net.learn.jetpack.data.datastore.discoveryStore

import net.learn.jetpack.data.database.TvDao
import net.learn.jetpack.data.model.tv.TvShow

class TvRoomStore(private val tvDao: TvDao) : BaseRoomDataStore<TvShow> {
    override suspend fun getDiscovery(page: Int): MutableList<TvShow>? {
        val response = tvDao.getAllDiscovery()
        return if (response.isEmpty()) null else response
    }

    override suspend fun addAll(sets: MutableList<TvShow>?) {
        sets?.let { tvDao.insertAll(sets) }
    }
}