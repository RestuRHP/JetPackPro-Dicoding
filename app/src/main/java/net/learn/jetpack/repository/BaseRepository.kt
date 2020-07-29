package net.learn.jetpack.repository

abstract class BaseRepository<DataStore> {
    protected var localStore: DataStore? = null
    protected var remoteStore: DataStore? = null

    fun init(localStore: DataStore, remoteStore: DataStore) {
        this.localStore = localStore
        this.remoteStore = remoteStore
    }

}