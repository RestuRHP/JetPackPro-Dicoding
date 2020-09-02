package net.learn.jetpack.service

import net.learn.jetpack.BuildConfig
import net.learn.jetpack.model.tv.TvResponse
import net.learn.submission4.data.response.MovieResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
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

    companion object {

        fun create(): Api {
            val logger = HttpLoggingInterceptor()
            logger.level = Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }
}