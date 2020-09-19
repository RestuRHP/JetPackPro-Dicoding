package net.learn.jetpack.ui.detail.movie.viewmodel

//import org.mockito.Mockito.`when`
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import net.learn.jetpack.data.model.movies.Movie
import net.learn.jetpack.data.repository.detail.DetailMovieRepository
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
class DetailMovieViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: DetailMovieRepository
    private lateinit var vm: DetailMovieViewModel
    private var observer = mock<Observer<DetailMovieState>>()
    private var movieSet = mutableListOf<Movie>()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        vm = DetailMovieViewModel(repository)
        vm.state.observeForever(observer)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `given detail viewModel when get similar movie should return success`() {
        movieSet.addAll(Dummy.generateDummyMovies())
        runBlocking {
            `when`(repository.getSimilarMovie(1)).thenReturn(movieSet)
            vm.getSimilarMovie(1)

            verify(repository, atLeastOnce()).getSimilarMovie(1)
            verify(observer).onChanged(DetailMovieState.ShowLoading)
            verify(observer).onChanged(DetailMovieState.HideLoading)
            verify(observer).onChanged(DetailMovieState.LoadSimilarMovieSuccess(movieSet))
        }
    }

    @Test
    fun `given detail viewModel when get similar movie should return error`() {
        movieSet.addAll(Dummy.generateDummyMovies())
        runBlocking {
            `when`(repository.getSimilarMovie(1)).thenThrow(IllegalThreadStateException())
            vm.getSimilarMovie(1)

            verify(repository, atLeastOnce()).getSimilarMovie(1)
            verify(observer).onChanged(DetailMovieState.ShowLoading)
            verify(observer).onChanged(DetailMovieState.HideLoading)
            verify(observer).onChanged(DetailMovieState.LoadScreenError)
        }
    }

    @Test
    fun `given detail viewmodel when check favorite movie should return true`() {
        movieSet.addAll(Dummy.generateDummyMovies())
        runBlocking {
            `when`(repository.isFavoriteMovie(1)).thenReturn(movieSet)
            vm.isFavoriteMovie(1)

            verify(repository, atLeastOnce()).isFavoriteMovie(1)
            verify(observer).onChanged(DetailMovieState.IsFavoriteTheater(true))
        }
    }

    @Test
    fun `given detail viewmodel when check favorite movie should return false`() {
        movieSet.addAll(Dummy.generateDummyMovies())
        runBlocking {
            `when`(repository.isFavoriteMovie(1)).thenReturn(null)
            vm.isFavoriteMovie(1)

            verify(repository, atLeastOnce()).isFavoriteMovie(1)
            verify(observer).onChanged(DetailMovieState.IsFavoriteTheater(false))
        }
    }

    @Test
    fun `given detail viewmodel when set favorite movie should return success`() {
        runBlocking {
            `when`(repository.setMovieToFavorite(1)).thenReturn(null)
            vm.setMovieToFavorite(1)

            verify(repository, atLeastOnce()).setMovieToFavorite(1)
            verify(observer).onChanged(DetailMovieState.SuccessAddFavorite)
        }
    }

    @Test
    fun `given detail viewmodel when remove favorite movie should return success`() {
        runBlocking {
            `when`(repository.removeMovieFromFavorite(1)).thenReturn(null)
            vm.removeMovieFromFavorite(1)

            verify(repository, atLeastOnce()).removeMovieFromFavorite(1)
            verify(observer).onChanged(DetailMovieState.SuccessRemoveFavorite)
        }
    }
}