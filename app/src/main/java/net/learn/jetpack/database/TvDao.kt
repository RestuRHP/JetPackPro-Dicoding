package net.learn.jetpack.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.learn.jetpack.model.tv.TvShow

@Dao
interface TvDao {
    @Query("SELECT * FROM TvShow ORDER BY VID ASC")
    suspend fun getAll(): MutableList<TvShow>

    @Query("DELETE FROM TvShow")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movie: MutableList<TvShow>)
}