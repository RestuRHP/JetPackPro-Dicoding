package net.learn.jetpack.data.datastore.favorite

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import net.learn.jetpack.data.database.AppDatabase
import net.learn.jetpack.data.model.movies.Movie

class FavoriteRoomStore(private val database: AppDatabase) : FavoriteRoomDataStore<Movie> {

    override suspend fun getAllFavorite(): LiveData<PagedList<Movie>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(6)
            .build()
        return LivePagedListBuilder(database.movieDao().getAllFavoriteMovie(true), config).build()
    }
}