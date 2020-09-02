package net.learn.jetpack.datastore.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import net.learn.jetpack.database.AppDatabase
import net.learn.jetpack.database.RemoteKeysMovie
import net.learn.jetpack.model.movies.Movie
import net.learn.jetpack.service.Api
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class MoviePaginationSource(
    private val service: Api,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, Movie>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        try {
            val page = when (loadType) {
                REFRESH -> null
                PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                APPEND -> {
                    val remoteKeysMovie = appDatabase.withTransaction {
//                        appDatabase.remoteKeysMovieDao().remoteKeysMovieId()
                    }
                }
            }
            return MediatorResult.Success(endOfPaginationReached = true)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)

        }
    }

    private suspend fun getLastItem(state: PagingState<Int, Movie>): RemoteKeysMovie? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                movie.id?.let { appDatabase.remoteKeysMovieDao().remoteKeysMovieId(it) }
            }
    }
}