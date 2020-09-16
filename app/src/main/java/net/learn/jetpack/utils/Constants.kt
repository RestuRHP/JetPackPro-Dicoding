package net.learn.jetpack.utils

import net.learn.jetpack.BuildConfig

object Network {

    const val GET_DISCOVERY_MOVIE =
        "discover/movie?api_key=${BuildConfig.API_KEY}&language=en-US&sort_by=vote_count.desc"
    const val GET_DISCOVERY_TV =
        "discover/tv?api_key=${BuildConfig.API_KEY}&language=en-US&sort_by=vote_count.desc"
    const val SIMILAR_MOVIE =
        "movie/{movie_id}/similar?api_key=${BuildConfig.API_KEY}&language=en-US"
    const val SIMILAR_TV = "tv/{tv_id}/similar?api_key=${BuildConfig.API_KEY}&language=en-US"
}

object Room {

    //Tv Query
    const val GET_ALL_DISCOVERY_TV = "SELECT * FROM TvShow ORDER BY voteCount DESC"
    const val DELETE_DISCOVERY_TV = "DELETE FROM TvShow"
    const val GET_ALL_TV_IS_FAVORITE = "SELECT * FROM TvShow WHERE favorite=:fvBool"
    const val UPDATE_TV_BE_FAVORITE = "UPDATE TvShow SET favorite=:fvBool WHERE id=:tvId"
    const val REMOVE_TV_FROM_FAVORITE = "UPDATE TvShow SET favorite=:fvBool WHERE id=:tvId"
    const val FILTER_TV_IS_FAVORITE_WITH_ID =
        "SELECT * FROM TvShow WHERE favorite=:fvBool AND id=:tvId LIMIT 1"

    //Movie Query
    const val GET_ALL_DISCOVERY_MOVIE = "SELECT * FROM Movie ORDER BY voteCount DESC"
    const val DELETE_DISCOVERY_MOVIE = "DELETE FROM Movie"
    const val GET_ALL_MOVIE_IS_FAVORITE = "SELECT * FROM Movie WHERE favorite=:fvBool"
    const val UPDATE_MOVIE_BE_FAVORITE = "UPDATE Movie SET favorite=:fvBool WHERE id=:movieId"
    const val REMOVE_MOVIE_FROM_FAVORITE = "UPDATE Movie SET favorite=:fvBool WHERE id=:movieId"
    const val FILTER_MOVIE_IS_FAVORITE_WITH_ID =
        "SELECT * FROM Movie WHERE favorite=:fvBool AND id=:movieId LIMIT 1"
}