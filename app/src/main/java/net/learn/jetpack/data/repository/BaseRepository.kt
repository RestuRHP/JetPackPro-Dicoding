package net.learn.jetpack.data.repository

import net.learn.jetpack.data.datastore.favorite.FavoriteRoomDataStore
import net.learn.jetpack.data.datastore.discoveryStore.BaseRemoteDataStore
import net.learn.jetpack.data.datastore.discoveryStore.BaseRoomDataStore

abstract class BaseRepository<T> {
    protected var localStore: BaseRoomDataStore<T>? = null
    protected var remoteStore: BaseRemoteDataStore<T>? = null

    fun init(localStore: BaseRoomDataStore<T>, remoteStore: BaseRemoteDataStore<T>) {
        this.localStore = localStore
        this.remoteStore = remoteStore
    }

}

abstract class Favorite<Model> {
    protected var roomStore: FavoriteRoomDataStore<Model>? = null

    fun init(roomStore: FavoriteRoomDataStore<Model>) {
        this.roomStore = roomStore
    }
}