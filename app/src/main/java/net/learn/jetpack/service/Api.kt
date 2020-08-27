package net.learn.jetpack.service

import net.learn.jetpack.BuildConfig
import net.learn.jetpack.model.tv.TvResponse
import net.learn.submission4.data.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("discover/movie?api_key=${BuildConfig.API_KEY}&language=en-US")
    suspend fun getMovies(
        @Query("page") page: Int?
    ): Response<MovieResponse>

    @GET("discover/tv?api_key=${BuildConfig.API_KEY}&language=en-US")
    suspend fun getTv(
        @Query("page") page: Int
    ): Response<TvResponse>
}