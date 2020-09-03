package net.learn.jetpack.repository

import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import net.learn.jetpack.datastore.tv.TvDataStore
import net.learn.jetpack.model.tv.TvShow
import net.learn.jetpack.repository.tv.TvRepository
import net.learn.jetpack.utils.Dummy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
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
    fun getSets() {
        tvSet.addAll(Dummy.generateDummyTvShows())
        runBlocking {
            `when`(localStore?.getSets(1)).thenReturn(tvSet)
            val tv = tvRepository?.getSets()
            verify(localStore)?.getSets(1)
            assertNotNull(tv)
            assertEquals(tv?.get(0)?.title, "Law & Order: Special Victims Unit")
        }
    }

    @Test
    fun throwEx() {
        runBlocking {
            `when`(localStore?.getSets(1)).thenReturn(null)
            `when`(remoteStore?.getSets(1)).thenReturn(null)
            try {
                tvRepository?.getSets()
            } catch (ex: Exception) {

            }
        }
    }
}