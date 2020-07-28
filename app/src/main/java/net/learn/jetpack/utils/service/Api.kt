package net.learn.jetpack.utils.service

import net.learn.jetpack.BuildConfig
import net.learn.submission4.data.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("discover/{type}?")
    suspend fun getListItem(
        @Path("type") type: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US"
//        @Query("page") page: Int
    ): Response<MovieResponse>
}