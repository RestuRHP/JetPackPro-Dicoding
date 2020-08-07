package net.learn.jetpack.datastore.MovieStore

import net.learn.jetpack.database.MovieDao
import net.learn.submission4mvvm.model.movies.Movie

class MovieRoomStore(private val movieDao: MovieDao) : MovieDataStore {
    override suspend fun getSets(page: Int?): MutableList<Movie>? {
        val response = movieDao.getAll()
        return if (response.isEmpty()) null else response
    }

    override suspend fun addAll(sets: MutableList<Movie>?) {
        sets?.let { movieDao.insertAll(sets) }
    }
}