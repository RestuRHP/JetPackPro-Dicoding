package net.learn.jetpack.utils

import net.learn.submission4mvvm.model.movies.Movie

class MockTestUtil {
    companion object {
        fun mockMovie() = Movie(
            1, "", 0, "", "", "", "", "", 0.0, false
        )

        fun mockMovieList(): List<Movie> {
            val movies = ArrayList<Movie>()
            movies.add(mockMovie())
            movies.add(mockMovie())
            movies.add(mockMovie())
            return movies
        }
    }
}