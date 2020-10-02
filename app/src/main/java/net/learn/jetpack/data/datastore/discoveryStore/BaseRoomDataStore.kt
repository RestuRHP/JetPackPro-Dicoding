package net.learn.jetpack.data.datastore.discoveryStore

interface BaseRoomDataStore<T> {
    suspend fun getDiscovery(page: Int): MutableList<T>?
    suspend fun addAll(sets: MutableList<T>?)
}