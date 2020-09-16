package net.learn.jetpack.data.repository.detail

import net.learn.jetpack.data.datastore.detail.BaseDetailRemoteDataStore
import net.learn.jetpack.data.datastore.detail.BaseDetailRoomDataStore

abstract class BaseDetailRepository<Models> {
    protected var remoteStoreBase: BaseDetailRemoteDataStore<Models>? = null
    protected var roomStoreBase: BaseDetailRoomDataStore<Models>? = null

    fun init(
        remoteStoreBase: BaseDetailRemoteDataStore<Models>,
        roomStoreBase: BaseDetailRoomDataStore<Models>
    ) {
        this.remoteStoreBase = remoteStoreBase
        this.roomStoreBase = roomStoreBase
    }

}