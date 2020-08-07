package net.learn.jetpack.repository

import kotlinx.coroutines.runBlocking
import net.learn.jetpack.datastore.TvStore.TvDataStore
import net.learn.jetpack.model.tv.TvShow
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TvRepositoryTest {
    @Mock
    var localStore: TvDataStore? = null

    @Mock
    var remoteStore: TvDataStore? = null
    var tvRepository: TvRepository? = null
    var tvSet = mutableListOf<TvShow>()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        tvRepository = TvRepository.instance.apply {
            init(localStore!!, remoteStore!!)
        }
    }

    @Test
    fun getDataFromLocal() {
        runBlocking {
            Mockito.`when`(localStore?.getSets(1)).thenReturn(tvSet)
            tvRepository?.getSets(1)

            Mockito.verify(remoteStore, Mockito.never())?.getSets(1)
            Mockito.verify(localStore, Mockito.never())?.addAll(tvSet)
        }
    }

    @Test
    fun getDataFromRemote() {
        runBlocking {
            Mockito.`when`(localStore?.getSets(1)).thenReturn(null)
            Mockito.`when`(remoteStore?.getSets(1)).thenReturn(tvSet)
            tvRepository?.getSets(1)

            Mockito.verify(remoteStore, Mockito.times(1))?.getSets(1)
            Mockito.verify(localStore, Mockito.times(1))?.addAll(tvSet)
        }
    }

    @Test
    fun remoteThrowAnException() {
        runBlocking {
            Mockito.`when`(localStore?.getSets(1)).thenReturn(null)
            Mockito.`when`(remoteStore?.getSets(1)).thenAnswer { throw Exception() }

            try {
                tvRepository?.getSets(1)
            } catch (ex: Exception) {
            }
        }
    }
}