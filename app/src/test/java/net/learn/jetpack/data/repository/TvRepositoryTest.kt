package net.learn.jetpack.data.repository

import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import net.learn.jetpack.data.datastore.discoveryStore.BaseRemoteDataStore
import net.learn.jetpack.data.datastore.discoveryStore.BaseRoomDataStore
import net.learn.jetpack.data.model.tv.TvShow
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
    var localStore: BaseRoomDataStore<TvShow>? = null

    @Mock
    var remoteStore: BaseRemoteDataStore<TvShow>? = null
    private var tvRepository: TvRepository? = null
    private var tvSet = mutableListOf<TvShow>()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        tvRepository = TvRepository.instance.apply {
            init(localStore!!, remoteStore!!)
        }
    }

    @Test
    fun `given repository when get discovery tv should return success`() {
        tvSet.addAll(Dummy.generateDummyTvShows())
        runBlocking {
            `when`(localStore?.getDiscovery(1)).thenReturn(tvSet)
            val tv = tvRepository?.loadDiscoveryTv()
            verify(localStore)?.getDiscovery(1)
            assertNotNull(tv)
            assertEquals(tv?.get(0)?.title, "Law & Order: Special Victims Unit")
        }
    }

    @Test
    fun `given repository when get discovery tv should return error`() {
        runBlocking {
            `when`(localStore?.getDiscovery(1)).thenReturn(null)
            `when`(remoteStore?.getDiscovery(1)).thenReturn(null)
            try {
                tvRepository?.loadDiscoveryTv()
            } catch (ex: Exception) {

            }
        }
    }
}