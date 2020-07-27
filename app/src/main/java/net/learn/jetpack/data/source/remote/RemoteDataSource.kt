package net.learn.jetpack.data.source.remote

import net.learn.jetpack.data.source.remote.response.Movie
import net.learn.jetpack.utils.JsonHelper

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null
        fun getInstance(helper: JsonHelper): RemoteDataSource = instance ?: synchronized(this) {
            instance ?: RemoteDataSource(helper)
        }
    }

    fun getItem(type: String): List<Movie> = jsonHelper.loadItem(type = type)

}