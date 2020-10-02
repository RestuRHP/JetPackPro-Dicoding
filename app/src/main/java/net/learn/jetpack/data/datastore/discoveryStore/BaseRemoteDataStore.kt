package net.learn.jetpack.data.datastore.discoveryStore


interface BaseRemoteDataStore<T> {
    suspend fun getDiscovery(page: Int): MutableList<T>?
}