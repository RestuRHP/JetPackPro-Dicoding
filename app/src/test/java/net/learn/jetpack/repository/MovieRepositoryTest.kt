package net.learn.jetpack.repository

import androidx.paging.PagingData
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import net.learn.jetpack.database.AppDatabase
import net.learn.jetpack.model.movies.Movie
import net.learn.jetpack.repository.movie.MovieRepository
import net.learn.jetpack.service.Api
import net.learn.jetpack.ui.movies.MovieAdapter
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MovieRepositoryTest {

    @Mock
    var movieRepository: MovieRepository? = null
    private lateinit var adapter: MovieAdapter
    val service = mock<Api>()
    val database = mock<AppDatabase>()
    private var movie: Flow<PagingData<Movie>>? = null

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        movieRepository = MovieRepository(service, database)
    }

    @Test
    fun getSets() {
        val mov = Movie(
            1,
            "",
            "",
            "",
            "",
            "",
            "",
            0.0,
            1,
            false
        )
        adapter = MovieAdapter()
        runBlocking {
            val pageSource = { database.movieDao().getAll() }
            movie = movieRepository?.getMovie()

            movie?.collect { it ->

            }

            assertNotNull(movie)
        }
    }
}