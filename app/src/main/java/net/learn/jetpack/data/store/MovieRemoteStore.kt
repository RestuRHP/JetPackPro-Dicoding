package net.learn.jetpack.data.store

import android.util.Log
import net.learn.jetpack.utils.service.Api
import net.learn.submission4mvvm.model.movies.Movie

class MovieRemoteStore(private val api: Api) : MovieDataStore {
    override suspend fun getSets(): MutableList<Movie>? {
        val response = api.getListItem("movie")
        if (response.isSuccessful)
            Log.d("Repository Set Movie", "List Movie :${response.body()?.results}")
        return response.body()?.results

//        throw Exception("Terjadi kesalahan saat melakukan request data, status error ${response.code()}")
    }

    override suspend fun addAll(sets: MutableList<Movie>?) {
    }

}