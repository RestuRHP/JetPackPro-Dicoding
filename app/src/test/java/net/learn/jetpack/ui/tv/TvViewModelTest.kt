package net.learn.jetpack.ui.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import net.learn.jetpack.data.model.tv.TvShow
import net.learn.jetpack.data.repository.TvRepository
import net.learn.jetpack.ui.BaseViewState
import net.learn.jetpack.ui.tv.viewmodel.TvViewModel
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
class TvViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var tvRepository: TvRepository
    private lateinit var tvViewModel: TvViewModel
    private var observer = mock<Observer<BaseViewState>>()
    private var tvSet = mutableListOf<TvShow>()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        tvViewModel = TvViewModel(tvRepository)
        tvViewModel.state?.observeForever(observer)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `given movies viewModel when get tv should return success`() {
        tvSet.addAll(Dummy.generateDummyTvShows())
        runBlocking {
            `when`(tvRepository.loadDiscoveryTv()).thenReturn(tvSet)
            tvViewModel.getDiscoveryTv()

            verify(tvRepository, atLeastOnce()).loadDiscoveryTv()
            verify(observer).onChanged(BaseViewState.ShowLoading)
            verify(observer).onChanged(BaseViewState.HideLoading)
            verify(observer).onChanged(BaseViewState.LoadTvSuccess(tvSet))

        }
    }

    @Test
    fun `given movies viewModel when get tv should return error`() {
        runBlocking {
            `when`(tvRepository.loadDiscoveryTv()).thenThrow(IllegalThreadStateException())
            tvViewModel.getDiscoveryTv()

            verify(tvRepository, atLeastOnce()).loadDiscoveryTv()
            verify(observer).onChanged(BaseViewState.LoadTvSuccess(null))
            verify(observer).onChanged(BaseViewState.ShowLoading)
            verify(observer).onChanged(BaseViewState.HideLoading)
            verify(observer).onChanged(BaseViewState.Error)
        }
    }

    @Test
    fun `given paging viewModel when get next tv should return success`() {
        tvSet.addAll(Dummy.generateDummyTvShows())
        runBlocking {
            `when`(tvRepository.paginationSets()).thenReturn(null)
            `when`(tvRepository.getDiscoveryTvFromDB()).thenReturn(tvSet)
            tvViewModel.listScrolled(1, 1, 2)
            verify(observer).onChanged(BaseViewState.LoadTvSuccess(tvSet))
        }
    }

    @Test
    fun `given paging viewModel when get next tv should return fail`() {
        runBlocking {
            `when`(tvRepository.paginationSets()).thenReturn(null)
            `when`(tvRepository.getDiscoveryTvFromDB()).thenReturn(null)
            tvViewModel.listScrolled(1, 1, 2)
            verify(observer, times(2)).onChanged(BaseViewState.LoadTvSuccess(null))
        }
    }
}