package net.learn.jetpack.repository.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.learn.jetpack.database.AppDatabase
import net.learn.jetpack.model.movies.Movie
import net.learn.jetpack.service.Api


class MovieRepository(private val service: Api, private val database: AppDatabase) {

    fun getMovie(): Flow<PagingData<Movie>> {
        val pagingSourceFactory = { database.movieDao().getAll() }
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = PageDataSource(service, database),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}