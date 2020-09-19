package net.learn.jetpack.data.repository.detail

import net.learn.jetpack.data.model.movies.Movie

interface DetailMovieRepositoryContract {
    suspend fun getSimilarMovie(movieId: Int): MutableList<Movie>?
    suspend fun setMovieToFavorite(movieId: Int)
    suspend fun removeMovieFromFavorite(movieId: Int)
    suspend fun isFavoriteMovie(movieId: Int): MutableList<Movie>?
}

class DetailMovieRepository private constructor() :
    DetailMovieRepositoryContract, BaseDetailRepository<Movie>() {

    override suspend fun getSimilarMovie(movieId: Int): MutableList<Movie>? =
        remoteStoreBase?.getSimilar(movieId)

    override suspend fun setMovieToFavorite(movieId: Int) {
        roomStoreBase?.setToFavorite(movieId)
    }

    override suspend fun removeMovieFromFavorite(movieId: Int) {
        roomStoreBase?.removeFromFavorite(movieId)
        throw Exception("error")
    }

    override suspend fun isFavoriteMovie(movieId: Int): MutableList<Movie>? =
        roomStoreBase?.isFavorite(movieId)

    companion object {
        val instance by lazy { DetailMovieRepository() }
    }
}