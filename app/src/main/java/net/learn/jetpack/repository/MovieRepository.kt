package net.learn.jetpack.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import net.learn.jetpack.datastore.movies.MovieDataStore
import net.learn.jetpack.model.movies.Movie
import net.learn.jetpack.ui.BaseViewState

private const val MOVIE_STARTING_PAGE_INDEX = 1

class MovieRepository private constructor() : BaseRepository<MovieDataStore>() {
    private var nextRequestPage = MOVIE_STARTING_PAGE_INDEX
    private var isRequestInProgress = false
    private var state = MutableLiveData<BaseViewState<Movie>>()

//    private var sourceFactory:DataSource.Factory<Int,Movie>()
//        get()

//    private var keySource: PagingSource<Int, Movie>()
//    get()=loadPage()

    suspend fun getSets(): MutableList<Movie>? {
        if (loadPage() != null) return loadPage()
        saveToDB(nextRequestPage)
        return loadPage()
    }

    suspend fun paginationSets() {
        if (isRequestInProgress) return
        val successful = saveToDB(nextRequestPage)
        if (successful) {
            nextRequestPage++
        }
    }

    private suspend fun saveToDB(page: Int?): Boolean {
        isRequestInProgress = true
        var successful = false
        try {
            state.postValue(BaseViewState(loading = true))
            val response = remoteStore?.getSets(page = page)
            localStore?.addAll(response)
            successful = true
        } catch (ex: Exception) {
            Log.d("Exception", "$ex")
            state.postValue(BaseViewState(loading = false, error = ex))
        }
        isRequestInProgress = false
        return successful
    }

    suspend fun loadPage(): MutableList<Movie>? {
        val cache = localStore?.getSets(nextRequestPage)
        return cache
    }

    companion object {
        val instance by lazy { MovieRepository() }
    }
}