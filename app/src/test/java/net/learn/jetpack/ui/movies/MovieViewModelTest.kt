package net.learn.jetpack.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import net.learn.jetpack.data.model.movies.Movie
import net.learn.jetpack.data.repository.MovieRepository
import net.learn.jetpack.ui.movies.viewmodel.MovieState
import net.learn.jetpack.ui.movies.viewmodel.MovieViewModel
import net.learn.jetpack.utils.Dummy
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
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
    private var observer = mock<Observer<MovieState>>()
    private var movieSet = mutableListOf<Movie>()


    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        movieViewModel = MovieViewModel(movieRepository)
        movieViewModel.state?.observeForever(observer)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `given movies viewModel when get movie should return success`() {
        movieSet.addAll(Dummy.generateDummyMovies())
        runBlocking {
            `when`(movieRepository.loadDiscoveryMovie()).thenReturn(movieSet)
            movieViewModel.getDiscoveryMovie()

            verify(movieRepository, atLeastOnce()).loadDiscoveryMovie()
            verify(observer).onChanged(MovieState.ShowLoading)
            verify(observer).onChanged(MovieState.HideLoading)
            verify(observer).onChanged(MovieState.LoadMovieSuccess(movieSet))

        }
    }

    @Test
    fun `given movies viewModel when get movie should return error`() {
        runBlocking {
            `when`(movieRepository.loadDiscoveryMovie()).thenThrow(IllegalThreadStateException())
            movieViewModel.getDiscoveryMovie()

            verify(movieRepository, atLeastOnce()).loadDiscoveryMovie()
            verify(observer).onChanged(MovieState.LoadMovieSuccess(null))
            verify(observer).onChanged(MovieState.ShowLoading)
            verify(observer).onChanged(MovieState.HideLoading)
            verify(observer).onChanged(MovieState.Error)
        }
    }

    @Test
    fun `given paging viewModel when get next movie should return success`() {
        movieSet.addAll(Dummy.generateDummyMovies())
        runBlocking {
//            `when`(movieRepository.paginationSets()).thenReturn(null)
            `when`(movieRepository.getDiscoveryMovieFromDB()).thenReturn(movieSet)
            movieViewModel.listScrolled(1, 1, 2)
            verify(observer).onChanged(MovieState.LoadMovieSuccess(movieSet))
        }
    }

    @Test
    fun `given paging viewModel when get next movie should return fail`() {
        runBlocking {
            `when`(movieRepository.paginationSets()).thenReturn(null)
            `when`(movieRepository.getDiscoveryMovieFromDB()).thenReturn(null)
            movieViewModel.listScrolled(1, 1, 2)
            verify(observer).onChanged(MovieState.LoadMovieSuccess(null))
        }
    }
}