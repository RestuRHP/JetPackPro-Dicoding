package net.learn.jetpack.service

import net.learn.jetpack.BuildConfig
import net.learn.jetpack.model.detail.Detail
import net.learn.jetpack.model.tv.TvResponse
import net.learn.submission4.data.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("discover/movie?api_key=${BuildConfig.API_KEY}&language=en-US&sort_by=vote_count.desc")
    suspend fun getMovies(
        @Query("page") page: Int?
    ): MovieResponse

    @GET("discover/tv?api_key=${BuildConfig.API_KEY}&language=en-US")
    suspend fun getTv(
        @Query("page") page: Int
    ): Response<TvResponse>

    @GET("3/movie/{id}?api_key=${BuildConfig.API_KEY}&language=en-US")
    suspend fun getDetail(
        @Path("id") id: String?
    ): Detail
}