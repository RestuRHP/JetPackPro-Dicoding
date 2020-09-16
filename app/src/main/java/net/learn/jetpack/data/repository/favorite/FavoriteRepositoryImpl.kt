package net.learn.jetpack.data.repository.favorite

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import net.learn.jetpack.data.model.movies.Movie
import net.learn.jetpack.data.repository.Favorite

interface FavoriteRepository {
    suspend fun getFavoriteMovie(): LiveData<PagedList<Movie>>?
}

class FavoriteRepositoryImpl private constructor() :
    FavoriteRepository, Favorite<Movie>() {
    override suspend fun getFavoriteMovie(): LiveData<PagedList<Movie>>? =
        roomStore?.getAllFavorite()

    companion object {
        val instance by lazy { FavoriteRepositoryImpl() }
    }
}