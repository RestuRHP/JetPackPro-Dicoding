package net.learn.jetpack.data.datastore.detail

import net.learn.jetpack.data.database.MovieDao
import net.learn.jetpack.data.model.movies.Movie

class DetailMovieRoomStore(private val movieDao: MovieDao) : BaseDetailRoomDataStore<Movie> {

    override suspend fun setToFavorite(movieId: Int) {
        movieDao.setMovieBeFavorite(true, movieId)
    }

    override suspend fun removeFromFavorite(movieId: Int) {
        movieDao.removeMovieFromFavorite(false, movieId)
    }

    override suspend fun isFavorite(movieId: Int): MutableList<Movie> =
        movieDao.movieIsFavorite(true, movieId)
}