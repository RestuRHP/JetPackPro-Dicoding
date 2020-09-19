package net.learn.jetpack.ui.favorite.tv.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import net.learn.jetpack.data.model.tv.TvShow
import net.learn.jetpack.data.repository.favorite.FavoriteTvRepositoryImpl
import net.learn.jetpack.utils.Dummy
import net.learn.jetpack.utils.mockPagedList
import org.junit.Assert.assertNotNull
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
class FavoriteTvViewModelImplTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: FavoriteTvRepositoryImpl
    private lateinit var vm: FavoriteTvViewModelImpl
    private var observer = mock<Observer<FavoriteTvState>>()
    private var oserv = mock<Observer<PagedList<TvShow>>>()
    private var movieSet = mutableListOf<TvShow>()


    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        vm = FavoriteTvViewModelImpl(repository)
        vm.state.observeForever(observer)
        runBlocking {
            vm.tvShow()?.observeForever(oserv)
        }
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `given fav viewmodel when get all data should return success`() {
        runBlocking {
            val dataSourceFactory =
                Mockito.mock(LiveData::class.java) as LiveData<PagedList<TvShow>>
            Mockito.`when`(repository.getFavoriteTv()).thenReturn(dataSourceFactory)


            val allFavorite = mockPagedList(Dummy.generateDummyTvShows())
            vm.tvShow()
            verify(repository, atLeastOnce()).getFavoriteTv()
            assertNotNull(allFavorite)
        }
    }
}