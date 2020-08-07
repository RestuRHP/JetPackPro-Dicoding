package net.learn.jetpack.utils.service

import net.learn.jetpack.BuildConfig
import net.learn.jetpack.model.tv.TvResponse
import net.learn.submission4.data.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int?
    ): Response<MovieResponse>

    @GET("discover/tv")
    suspend fun getTv(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): Response<TvResponse>
}