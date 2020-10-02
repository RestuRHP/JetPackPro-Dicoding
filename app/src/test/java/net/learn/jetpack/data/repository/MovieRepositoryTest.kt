package net.learn.jetpack.data.repository

import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import net.learn.jetpack.data.datastore.discoveryStore.BaseRemoteDataStore
import net.learn.jetpack.data.datastore.discoveryStore.BaseRoomDataStore
import net.learn.jetpack.data.model.movies.Movie
import net.learn.jetpack.utils.Dummy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MovieRepositoryTest {

    @Mock
    var localStore: BaseRoomDataStore<Movie>? = null

    @Mock
    var remoteStore: BaseRemoteDataStore<Movie>? = null
    private var movieRepository: MovieRepository? = null
    private var moviesItem = mutableListOf<Movie>()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        movieRepository = MovieRepository.instance.apply {
            init(localStore!!, remoteStore!!)
        }
    }

    @Test
    fun `given repository when get discovery movie should return success`() {
        moviesItem.addAll(Dummy.generateDummyMovies())
        runBlocking {
            `when`(localStore?.getDiscovery(1)).thenReturn(moviesItem)
            val movie = movieRepository?.loadDiscoveryMovie()
            verify(localStore)?.getDiscovery(1)
            assertNotNull(movie)
            assertEquals(movie?.get(0)?.title, "The Old Guard")
        }
    }
}