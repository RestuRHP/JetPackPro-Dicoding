package net.learn.jetpack.data.network

import net.learn.jetpack.data.model.tv.TvResponse
import net.learn.jetpack.utils.Network.GET_DISCOVERY_MOVIE
import net.learn.jetpack.utils.Network.GET_DISCOVERY_TV
import net.learn.jetpack.utils.Network.SIMILAR_MOVIE
import net.learn.jetpack.utils.Network.SIMILAR_TV
import net.learn.submission4.data.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET(GET_DISCOVERY_MOVIE)
    suspend fun getDiscoveryMovie(@Query("page") page: Int?): Response<MovieResponse>

    @GET(GET_DISCOVERY_TV)
    suspend fun getDiscoveryTv(@Query("page") page: Int): Response<TvResponse>

    @GET(SIMILAR_MOVIE)
    suspend fun getSimilarMovie(@Path("movie_id") movieId: Int): Response<MovieResponse>

    @GET(SIMILAR_TV)
    suspend fun getSimilarTvShow(@Path("tv_id") tvId: Int): Response<TvResponse>
}