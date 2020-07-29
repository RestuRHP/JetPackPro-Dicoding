package net.learn.jetpack.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.learn.submission4mvvm.model.movies.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM Movie ")
    suspend fun getAll(): MutableList<Movie>

    @Query("DELETE FROM Movie")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movie: MutableList<Movie>)
}