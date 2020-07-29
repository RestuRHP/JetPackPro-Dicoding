package net.learn.jetpack.repository

import kotlinx.coroutines.runBlocking
import net.learn.jetpack.ui.movies.store.MovieDataStore
import net.learn.submission4mvvm.model.movies.Movie
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class BaseRepositoryTest {
    @Mock
    var localDataStore: MovieDataStore? = null

    @Mock
    var remoteDataStore: MovieDataStore? = null

    var movieRepository: MovieRepository? = null

    var movieSet = mutableListOf<Movie>()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        movieRepository = MovieRepository.instance.apply {
            init(localDataStore!!, remoteDataStore!!)
        }
    }

    @Test
    fun shouldNotGetFromRemoteWhenLocalIsNotNull() {
        runBlocking {
            Mockito.`when`(localDataStore?.getSets()).thenReturn(movieSet)
            movieRepository?.getSets()

            Mockito.verify(remoteDataStore, Mockito.never())?.getSets()
            Mockito.verify(localDataStore, Mockito.never())?.addAll(movieSet)
        }
    }

    @Test
    fun shouldCallGetFromRemoteAndSaveToLocalWhenLocalIsNull() {
        runBlocking {
            Mockito.`when`(localDataStore?.getSets()).thenReturn(null)
            Mockito.`when`(remoteDataStore?.getSets()).thenReturn(movieSet)
            movieRepository?.getSets()

            Mockito.verify(remoteDataStore, Mockito.times(1))?.getSets()
            Mockito.verify(localDataStore, Mockito.times(1))?.addAll(movieSet)
        }
    }

    @Test
    fun shouldThrowExceptionWhenRemoteThrowAnException() {
        runBlocking {
            Mockito.`when`(localDataStore?.getSets()).thenReturn(null)
            Mockito.`when`(remoteDataStore?.getSets()).thenAnswer { throw Exception() }

            try {
                movieRepository?.getSets()
            } catch (ex: Exception) {
            }
        }
    }
}