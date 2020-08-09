package net.learn.jetpack.datastore.tv

import net.learn.jetpack.database.TvDao
import net.learn.jetpack.model.tv.TvShow

class TvRoomStore(private val tvDao: TvDao) : TvDataStore {
    override suspend fun getSets(page: Int): MutableList<TvShow>? {
        val response = tvDao.getAll()
        return if (response.isEmpty()) null else response
    }

    override suspend fun addAll(sets: MutableList<TvShow>?) {
        sets?.let { tvDao.insertAll(sets) }
    }
}