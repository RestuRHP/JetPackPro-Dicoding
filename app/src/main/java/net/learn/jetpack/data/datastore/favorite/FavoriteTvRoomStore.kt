package net.learn.jetpack.data.datastore.favorite

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import net.learn.jetpack.data.database.AppDatabase
import net.learn.jetpack.data.model.tv.TvShow

class FavoriteTvRoomStore(private val database: AppDatabase) : FavoriteRoomDataStore<TvShow> {
    override suspend fun getAllFavorite(): LiveData<PagedList<TvShow>>? {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .build()
        return LivePagedListBuilder(database.tvDao().getAllFavoriteTv(true), config).build()
    }
}