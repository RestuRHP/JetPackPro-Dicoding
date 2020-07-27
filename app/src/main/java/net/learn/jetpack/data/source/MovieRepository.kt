package net.learn.jetpack.data.source

import net.learn.jetpack.data.MovieEntity
import net.learn.jetpack.data.source.remote.RemoteDataSource

class MovieRepository private constructor(private val remoteDataSource: RemoteDataSource) :
    MovieDataSource {
    companion object {
        @Volatile
        private var instance: MovieRepository? = null
        fun getInstance(remoteData: RemoteDataSource): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteData)
            }
    }

    override fun getAllItem(type: String): List<MovieEntity> {
        val itemResponse = remoteDataSource.getItem(type = type)
        val itemList = ArrayList<MovieEntity>()
        for (response in itemResponse) {
            val item = MovieEntity(
                response.id,
                response.backdropPath,
                response.originalLanguage,
                response.overview,
                response.posterPath,
                response.releaseDate,
                response.title,
                response.voteAverage
            )
            itemList.add(item)
        }
        return itemList
    }

}