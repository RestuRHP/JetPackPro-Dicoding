package net.learn.jetpack.data.repository.favorite

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import kotlinx.coroutines.runBlocking
import net.learn.jetpack.data.datastore.favorite.FavoriteTvRoomStore
import net.learn.jetpack.data.model.tv.TvShow
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class FavoriteTvRepositoryImplTest {

    @Mock
    var roomStore: FavoriteTvRoomStore? = null
    private var repository: FavoriteTvRepositoryImpl? = null

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        repository = FavoriteTvRepositoryImpl.instance.apply {
            init(roomStore!!)
        }
    }

    @Test
    fun test() {
        runBlocking {
            val dataSourceFactory =
                Mockito.mock(LiveData::class.java) as LiveData<PagedList<TvShow>>
            Mockito.`when`(roomStore!!.getAllFavorite()).thenReturn(dataSourceFactory)

            val allFavorite = roomStore?.getAllFavorite()
            roomStore?.getAllFavorite()
            assertNotNull(allFavorite)
        }
    }

}