package net.learn.jetpack.data.datastore.detail

import net.learn.jetpack.data.database.TvDao
import net.learn.jetpack.data.model.tv.TvShow

class DetailTvRoomStore(private val tvDao: TvDao) : BaseDetailRoomDataStore<TvShow> {
    override suspend fun setToFavorite(movieId: Int) {
        tvDao.setTvBeFavorite(true, movieId)
    }

    override suspend fun removeFromFavorite(movieId: Int) {
        tvDao.removeTvFromFavorite(false, movieId)
    }

    override suspend fun isFavorite(movieId: Int): MutableList<TvShow> =
        tvDao.tvIsFavorite(true, movieId)
}