package net.learn.jetpack.data.datastore.favorite

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import net.learn.jetpack.data.database.AppDatabase
import net.learn.jetpack.data.model.movies.Movie

class FavoriteMovieRoomStore(private val database: AppDatabase) : FavoriteRoomDataStore<Movie> {

    override suspend fun getAllFavorite(): LiveData<PagedList<Movie>> {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .build()
        return LivePagedListBuilder(database.movieDao().getAllFavoriteMovie(true), config).build()
    }
}