package net.learn.jetpack.datastore.movies

import androidx.paging.PagingSource
import net.learn.jetpack.model.movies.Movie
import net.learn.jetpack.service.Api

class MovieRemoteStore(private val api: Api) : MovieDataStore {
    override suspend fun getSets(page: Int?): PagingSource<Int, Movie>? {
        TODO("Not yet implemented")
    }

    override suspend fun addAll(sets: MutableList<Movie>?) {
        TODO("Not yet implemented")
    }
//    override suspend fun getSets(page: Int?): PagingSource<Int, Movie>? {
//        EspressoIdlingResource.increment()
//        val response = api.getMovies(page = page)
//        return response
////        if (response.isSuccessful) {
////            EspressoIdlingResource.decrement()
////            return response.body()?.results
////        }
////        throw Exception("Terjadi kesalahan saat melakukan request data, status error ${response.code()}")
////        throw Exception("ASD")
//    }
//
//    override suspend fun addAll(sets: MutableList<Movie>?) {
//    }

}