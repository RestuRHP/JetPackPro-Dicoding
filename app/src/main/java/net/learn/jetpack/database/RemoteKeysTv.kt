package net.learn.jetpack.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys_tv")
data class RemoteKeysTv(
    @PrimaryKey val tv_id: Long,
    val prevKey: Int?,
    val nextKey: Int?
)