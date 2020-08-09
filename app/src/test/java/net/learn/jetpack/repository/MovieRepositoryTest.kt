package net.learn.jetpack.repository

import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import net.learn.jetpack.datastore.MovieStore.MovieDataStore
import net.learn.jetpack.utils.Dummy.Dummy
import net.learn.submission4mvvm.model.movies.Movie
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MovieRepositoryTest {

    @Mock
    var localStore: MovieDataStore? = null
    @Mock
    var remoteStore: MovieDataStore? = null
    var movieRepository: MovieRepository? = null
    var movieSet = mutableListOf<Movie>()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        movieRepository = MovieRepository.instance.apply {
            init(localStore!!, remoteStore!!)
        }
    }

    @Test
    fun getSets() {
        movieSet.addAll(Dummy.generateDummnyMovies())
        runBlocking {
            Mockito.`when`(localStore?.getSets(1)).thenReturn(movieSet)
            val movies = movieRepository?.getSets()
            verify(localStore)?.getSets(1)
            assertNotNull(movies)
            assertEquals(movies?.get(0)?.title, "The Old Guard")
        }
    }
}