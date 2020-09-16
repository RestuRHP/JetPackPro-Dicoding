package net.learn.jetpack.data.datastore.detail

interface BaseDetailRoomDataStore<Model> {
    suspend fun setToFavorite(movieId: Int)
    suspend fun removeFromFavorite(movieId: Int)
    suspend fun isFavorite(movieId: Int): MutableList<Model>
}