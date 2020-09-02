package net.learn.jetpack.repository

import androidx.paging.PagingData
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import net.learn.jetpack.database.AppDatabase
import net.learn.jetpack.model.movies.Movie
import net.learn.jetpack.service.Api
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MovieRepositoryTest {

    @Mock
    var movieRepository: MovieRepository? = null
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
        runBlocking {
            val pageSource = { database.movieDao().getAll() }
            movie = movieRepository?.getMovie()
            assertNotNull(movie)
        }
    }
}