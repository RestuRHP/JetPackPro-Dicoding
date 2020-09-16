package net.learn.jetpack.data.repository

import net.learn.jetpack.data.datastore.movies.MovieDataStore
import net.learn.jetpack.data.model.movies.Movie

private const val MOVIE_STARTING_PAGE_INDEX = 1

class MovieRepository private constructor() : BaseRepository<MovieDataStore>() {
    private var nextRequestPage = MOVIE_STARTING_PAGE_INDEX
    private var isRequestInProgress = false

    suspend fun loadDiscoveryMovie(): MutableList<Movie>? {
        val data = getDiscoveryMovieFromDB()
        return try {
            if (data != null) {
                data
            } else {
                saveDiscoveryMovieToDB(nextRequestPage)
                getDiscoveryMovieFromDB()
            }
        } catch (ex: Exception) {
            throw ex
        }
    }

    private suspend fun saveDiscoveryMovieToDB(page: Int): Boolean {
        isRequestInProgress = true
        var successful = false
        try {
            val response = remoteStore?.getDiscoveryMovie(page = page)
            if (response != null) {
                localStore?.addAll(response)
                successful = true
            }
        } catch (ex: Exception) {
            throw ex
        }
        isRequestInProgress = false
        return successful
    }

    suspend fun getDiscoveryMovieFromDB(): MutableList<Movie>? {
        return localStore?.getDiscoveryMovie(nextRequestPage)
    }

    suspend fun paginationSets() {
        if (isRequestInProgress) return
        val successful = saveDiscoveryMovieToDB(nextRequestPage)
        if (successful) {
            nextRequestPage++
        }
    }

    companion object {
        val instance by lazy { MovieRepository() }
    }
}