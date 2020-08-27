package net.learn.jetpack.datastore.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import net.learn.jetpack.database.AppDatabase
import net.learn.jetpack.datastore.movies.MovieRemoteStore
import net.learn.jetpack.model.movies.Movie
import net.learn.jetpack.ui.BaseViewState

@OptIn(ExperimentalPagingApi::class)
class PageDataSource(
    private val remoteStore: MovieRemoteStore,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, Movie>() {

    private val viewState = MutableLiveData<BaseViewState<Movie>>()
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        TODO("Not yet implemented")
    }

    private suspend fun getFirstItem(state: PagingState<Int, Movie>): MutableList<Movie>? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                appDatabase.movieDao().getAll()
            }
    }

    private suspend fun getLastItem(state: PagingState<Int, Movie>): MutableList<Movie>? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                appDatabase.movieDao().getAll()
            }
    }

    private suspend fun getClosestToCurrentPosition(state: PagingState<Int, Movie>): MutableList<Movie>? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id.let { movie ->
                appDatabase.movieDao().getAll()
            }
        }
    }

}