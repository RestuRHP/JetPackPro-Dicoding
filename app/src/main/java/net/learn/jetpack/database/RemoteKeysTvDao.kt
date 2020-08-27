package net.learn.jetpack.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysTvDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remote: List<RemoteKeysTv>)

    @Query("SELECT * FROM remote_keys_tv WHERE tv_id = :tvId")
    suspend fun remoteKeysMovieId(tvId: Long): RemoteKeysTv?

    @Query("DELETE FROM remote_keys_tv")
    suspend fun clearRemoteKeys()
}