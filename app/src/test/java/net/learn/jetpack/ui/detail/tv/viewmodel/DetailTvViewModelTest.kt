package net.learn.jetpack.ui.detail.tv.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import net.learn.jetpack.data.model.tv.TvShow
import net.learn.jetpack.data.repository.detail.DetailTvRepository
import net.learn.jetpack.ui.BaseViewState
import net.learn.jetpack.utils.Dummy
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailTvViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: DetailTvRepository
    private lateinit var vm: DetailTvViewModel
    private var observer = mock<Observer<BaseViewState>>()
    private var movieSet = mutableListOf<TvShow>()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        vm = DetailTvViewModel(repository)
        vm.state.observeForever(observer)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `given detail viewModel when get similar movie should return success`() {
        movieSet.addAll(Dummy.generateDummyTvShows())
        runBlocking {
            Mockito.`when`(repository.getSimilarTv(1)).thenReturn(movieSet)
            vm.getSimilarTv(1)

            Mockito.verify(repository, atLeastOnce()).getSimilarTv(1)
            Mockito.verify(observer).onChanged(BaseViewState.ShowLoading)
            Mockito.verify(observer).onChanged(BaseViewState.HideLoading)
            Mockito.verify(observer).onChanged(BaseViewState.LoadSimilarTvSuccess(movieSet))
        }
    }

    @Test
    fun `given detail viewModel when get similar movie should return error`() {
        movieSet.addAll(Dummy.generateDummyTvShows())
        runBlocking {
            Mockito.`when`(repository.getSimilarTv(1)).thenThrow(IllegalThreadStateException())
            vm.getSimilarTv(1)

            Mockito.verify(repository, atLeastOnce()).getSimilarTv(1)
            Mockito.verify(observer).onChanged(BaseViewState.ShowLoading)
            Mockito.verify(observer).onChanged(BaseViewState.HideLoading)
            Mockito.verify(observer).onChanged(BaseViewState.LoadScreenError)
        }
    }

    @Test
    fun `given detail viewmodel when check favorite movie should return true`() {
        movieSet.addAll(Dummy.generateDummyTvShows())
        runBlocking {
            Mockito.`when`(repository.isFavorite(1)).thenReturn(movieSet)
            vm.isFavorite(1)

            Mockito.verify(repository, atLeastOnce()).isFavorite(1)
            Mockito.verify(observer).onChanged(BaseViewState.IsFavoriteTheater(true))
        }
    }

    @Test
    fun `given detail viewmodel when check favorite movie should return false`() {
        movieSet.addAll(Dummy.generateDummyTvShows())
        runBlocking {
            Mockito.`when`(repository.isFavorite(1)).thenReturn(null)
            vm.isFavorite(1)

            Mockito.verify(repository, atLeastOnce()).isFavorite(1)
            Mockito.verify(observer).onChanged(BaseViewState.IsFavoriteTheater(false))
        }
    }

    @Test
    fun `given detail viewmodel when set favorite movie should return success`() {
        runBlocking {
            Mockito.`when`(repository.setTvToFavorite(1)).thenReturn(null)
            vm.setTvToFavorite(1)

            Mockito.verify(repository, atLeastOnce()).setTvToFavorite(1)
            Mockito.verify(observer).onChanged(BaseViewState.SuccessAddFavorite)
        }
    }

    @Test
    fun `given detail viewmodel when remove favorite movie should return success`() {
        runBlocking {
            Mockito.`when`(repository.removeTvFromFavorite(1)).thenReturn(null)
            vm.removeTvFromFavorite(1)

            Mockito.verify(repository, atLeastOnce()).removeTvFromFavorite(1)
            Mockito.verify(observer).onChanged(BaseViewState.SuccessRemoveFavorite)
        }
    }
}