package net.learn.jetpack.data


data class MovieEntity(
    var id: Int,
    var backdropPath: String,
    var originalLanguage: String,
    var overview: String,
    var posterPath: String,
    var releaseDate: String,
    var title: String,
    var voteAverage: Double
)