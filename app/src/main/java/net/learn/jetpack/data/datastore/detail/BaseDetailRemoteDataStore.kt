package net.learn.jetpack.data.datastore.detail

interface BaseDetailRemoteDataStore<Model> {
    suspend fun getSimilar(movieId: Int): MutableList<Model>?
}