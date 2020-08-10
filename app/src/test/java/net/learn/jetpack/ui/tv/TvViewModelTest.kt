package net.learn.jetpack.ui.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import net.learn.jetpack.model.tv.TvShow
import net.learn.jetpack.repository.TvRepository
import net.learn.jetpack.utils.Dummy.Dummy
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
class TvViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var tvRepository:TvRepository
    private lateinit var tvViewModel:TvViewModel
    private var tvSet= mutableListOf<TvShow>()

    @Before
    fun init(){
        MockitoAnnotations.initMocks(this)
        tvViewModel= TvViewModel(tvRepository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun getSets() {
        tvSet.addAll(Dummy.generateDummyTvShows())
        runBlocking {
            `when`(tvRepository.getSets()).thenReturn(tvSet)
            tvViewModel.getSets()
            val state = tvViewModel.viewState.value!!
            assertFalse(state.loading)
            assertNull(state.error)
            assertNotNull(state.data)
        }
    }

    @Test
    fun listScrolled() {
        tvSet.addAll(Dummy.generateDummyTvShows())
        runBlocking {
            `when`(tvRepository.paginationSets()).thenReturn(null)
            `when`(tvRepository.loadPage()).thenReturn(tvSet)
            tvViewModel.listScrolled(1,1,2)
            val state = tvViewModel.viewState.value!!
            assertFalse(state.loading)
            assertNull(state.error)
            assertNotNull(state.data)
        }
    }
}