package net.learn.jetpack.data.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.learn.jetpack.data.model.movies.Movie
import net.learn.jetpack.utils.Room.DELETE_DISCOVERY_MOVIE
import net.learn.jetpack.utils.Room.FILTER_MOVIE_IS_FAVORITE_WITH_ID
import net.learn.jetpack.utils.Room.GET_ALL_DISCOVERY_MOVIE
import net.learn.jetpack.utils.Room.GET_ALL_MOVIE_IS_FAVORITE
import net.learn.jetpack.utils.Room.REMOVE_MOVIE_FROM_FAVORITE
import net.learn.jetpack.utils.Room.UPDATE_MOVIE_BE_FAVORITE

@Dao
interface MovieDao {
    @Query(GET_ALL_DISCOVERY_MOVIE)
    suspend fun getDiscovery(): MutableList<Movie>

    @Query(DELETE_DISCOVERY_MOVIE)
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movie: MutableList<Movie>)

    @Query(UPDATE_MOVIE_BE_FAVORITE)
    suspend fun setMovieBeFavorite(fvBool: Boolean, movieId: Int)

    @Query(REMOVE_MOVIE_FROM_FAVORITE)
    suspend fun removeMovieFromFavorite(fvBool: Boolean, movieId: Int)

    @Query(FILTER_MOVIE_IS_FAVORITE_WITH_ID)
    suspend fun movieIsFavorite(fvBool: Boolean, movieId: Int): MutableList<Movie>

    @Query(GET_ALL_MOVIE_IS_FAVORITE)
    fun getAllFavoriteMovie(fvBool: Boolean): DataSource.Factory<Int, Movie>
}