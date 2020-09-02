package net.learn.jetpack.datastore.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import net.learn.jetpack.database.AppDatabase
import net.learn.jetpack.database.RemoteKeysMovie
import net.learn.jetpack.model.movies.Movie
import net.learn.jetpack.service.Api
import net.learn.jetpack.utils.EspressoIdlingResource
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class PageDataSource(
    private val service: Api,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, Movie>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeysMovie = getClosestToCurrentPosition(state)
                remoteKeysMovie?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeysMovie = getFirstItem(state)
                remoteKeysMovie?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val remoteKeysMovie = getLastItem(state)
                remoteKeysMovie?.nextKey
            }
        }

        EspressoIdlingResource.increment()
        try {
            val apiResponse = service.getMovies(page)
            val movie = apiResponse.results
            val endOfPaginationReached = movie.isEmpty()

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.remoteKeysMovieDao().clearRemoteKeys()
                    appDatabase.movieDao().deleteAll()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page?.minus(1)
                val nextKey = if (endOfPaginationReached) null else page?.plus(1)
                val key = movie.map {
                    RemoteKeysMovie(movie_id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                appDatabase.remoteKeysMovieDao().insertAll(key)
                appDatabase.movieDao().insertAll(movie)
            }
            EspressoIdlingResource.decrement()
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            EspressoIdlingResource.decrement()
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            EspressoIdlingResource.decrement()
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getFirstItem(state: PagingState<Int, Movie>): RemoteKeysMovie? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                movie.id?.let { appDatabase.remoteKeysMovieDao().remoteKeysMovieId(it) }
            }
    }

    private suspend fun getLastItem(state: PagingState<Int, Movie>): RemoteKeysMovie? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                movie.id?.let { appDatabase.remoteKeysMovieDao().remoteKeysMovieId(it) }
            }
    }

    private suspend fun getClosestToCurrentPosition(state: PagingState<Int, Movie>): RemoteKeysMovie? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { movieId ->
                appDatabase.remoteKeysMovieDao().remoteKeysMovieId(movieId)
            }
        }
    }

}