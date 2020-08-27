package net.learn.jetpack.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys_movie")
data class RemoteKeysMovie(
    @PrimaryKey val movie_id: Int?,
    val prevKey: Int?,
    val nextKey: Int?
)