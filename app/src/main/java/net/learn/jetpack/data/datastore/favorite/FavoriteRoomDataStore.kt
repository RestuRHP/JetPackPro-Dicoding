package net.learn.jetpack.data.datastore.favorite

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

interface FavoriteRoomDataStore<Model> {
    suspend fun getAllFavorite(): LiveData<PagedList<Model>>?
}