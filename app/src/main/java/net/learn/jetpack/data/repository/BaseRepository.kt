package net.learn.jetpack.data.repository

import net.learn.jetpack.data.datastore.favorite.FavoriteRoomDataStore

abstract class BaseRepository<DataStore> {
    protected var localStore: DataStore? = null
    protected var remoteStore: DataStore? = null

    fun init(localStore: DataStore, remoteStore: DataStore) {
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