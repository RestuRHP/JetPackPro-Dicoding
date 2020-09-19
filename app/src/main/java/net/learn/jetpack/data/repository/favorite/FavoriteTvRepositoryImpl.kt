package net.learn.jetpack.data.repository.favorite

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import net.learn.jetpack.data.model.tv.TvShow
import net.learn.jetpack.data.repository.Favorite


interface FavoriteTvRepository {
    suspend fun getFavoriteTv(): LiveData<PagedList<TvShow>>?
}

class FavoriteTvRepositoryImpl private constructor() : FavoriteTvRepository, Favorite<TvShow>() {
    override suspend fun getFavoriteTv(): LiveData<PagedList<TvShow>>? =
        roomStore?.getAllFavorite()

    companion object {
        val instance by lazy { FavoriteTvRepositoryImpl() }
    }
}