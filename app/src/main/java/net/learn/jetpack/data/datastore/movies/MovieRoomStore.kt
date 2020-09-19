package net.learn.jetpack.data.datastore.movies

import net.learn.jetpack.data.database.MovieDao
import net.learn.jetpack.data.model.movies.Movie

class MovieRoomStore(private val movieDao: MovieDao) : MovieDataStore {
    override suspend fun getDiscoveryMovie(page: Int): MutableList<Movie>? {
        val response = movieDao.getDiscovery()
        return if (response.isEmpty()) null else response
    }

    override suspend fun addAll(sets: MutableList<Movie>?) {
        sets?.let { movieDao.insertAll(sets) }
    }

}