package net.learn.jetpack.datastore.movies

import androidx.paging.PagingSource
import net.learn.jetpack.database.MovieDao
import net.learn.jetpack.model.movies.Movie

class MovieRoomStore(private val movieDao: MovieDao) : MovieDataStore {
    override suspend fun getSets(page: Int?): PagingSource<Int, Movie>? {
        TODO("Not yet implemented")
    }

    override suspend fun addAll(sets: MutableList<Movie>?) {
        TODO("Not yet implemented")
    }
//    override suspend fun getSets(page: Int?): PagingSource<Int, Movie>? {
//        val response = movieDao.getAll()
////        return if (response.isEmpty()) null else response
//        return response
//    }
//
//    override suspend fun addAll(sets: MutableList<Movie>?) {
//        sets?.let { movieDao.insertAll(sets) }
//    }
}