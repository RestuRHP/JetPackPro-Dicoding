package net.learn.jetpack.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.learn.jetpack.model.movies.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM Movie ORDER BY voteCount DESC")
    fun getAll(): PagingSource<Int, Movie>

    @Query("DELETE FROM Movie")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movie: List<Movie>)
}