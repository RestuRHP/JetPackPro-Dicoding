package net.learn.jetpack.repository

import kotlinx.coroutines.runBlocking
import net.learn.jetpack.datastore.MovieStore.MovieDataStore
import net.learn.submission4mvvm.model.movies.Movie
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
    fun getDataFromLocal() {
        runBlocking {
            Mockito.`when`(localStore?.getSets(1)).thenReturn(movieSet)
            movieRepository?.getSets(1)

            Mockito.verify(remoteStore, Mockito.never())?.getSets(1)
            Mockito.verify(localStore, Mockito.never())?.addAll(movieSet)
        }
    }

    @Test
    fun getDataFromRemote() {
        runBlocking {
            Mockito.`when`(localStore?.getSets(1)).thenReturn(null)
            Mockito.`when`(remoteStore?.getSets(1)).thenReturn(movieSet)
            movieRepository?.getSets(1)

            Mockito.verify(remoteStore, Mockito.times(1))?.getSets(1)
            Mockito.verify(localStore, Mockito.times(1))?.addAll(movieSet)
        }
    }

    @Test
    fun remoteThrowAnException() {
        runBlocking {
            Mockito.`when`(localStore?.getSets(1)).thenReturn(null)
            Mockito.`when`(remoteStore?.getSets(1)).thenAnswer { throw Exception() }

            try {
                movieRepository?.getSets(1)
            } catch (ex: Exception) {
            }
        }
    }
}