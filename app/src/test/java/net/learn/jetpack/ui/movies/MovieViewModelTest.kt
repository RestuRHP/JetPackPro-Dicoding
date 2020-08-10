package net.learn.jetpack.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import net.learn.jetpack.model.movies.Movie
import net.learn.jetpack.repository.MovieRepository
import net.learn.jetpack.utils.Dummy
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository
    private lateinit var movieViewModel: MovieViewModel

    private var movieSet = mutableListOf<Movie>()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        movieViewModel = MovieViewModel(movieRepository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }


    @Test
    fun getSets() {
        movieSet.addAll(Dummy.generateDummyMovies())
        runBlocking {
            `when`(movieRepository.getSets()).thenReturn(movieSet)
            movieViewModel.getSets()
            val state = movieViewModel.viewState.value!!
            assertFalse(state.loading)
            assertNull(state.error)
            assertNotNull(state.data)
        }
    }

    @Test
    fun listScrolled() {
        movieSet.addAll(Dummy.generateDummyMovies())
        runBlocking {
            `when`(movieRepository.paginationSets()).thenReturn(null)
            `when`(movieRepository.loadPage()).thenReturn(movieSet)
            movieViewModel.listScrolled(1,1,2)
            val state = movieViewModel.viewState.value!!
            assertFalse(state.loading)
            assertNull(state.error)
            assertNotNull(state.data)
        }
    }
}