package net.learn.jetpack.data.repository.favorite

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import kotlinx.coroutines.runBlocking
import net.learn.jetpack.data.datastore.favorite.FavoriteMovieRoomStore
import net.learn.jetpack.data.model.movies.Movie
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class FavoriteMovieRepositoryImplTest {

    @Mock
    var roomStore: FavoriteMovieRoomStore? = null
    private var repository: FavoriteMovieRepositoryImpl? = null

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        repository = FavoriteMovieRepositoryImpl.instance.apply {
            init(roomStore!!)
        }
    }

    @Test
    fun test() {
        runBlocking {
            val dataSourceFactory = Mockito.mock(LiveData::class.java) as LiveData<PagedList<Movie>>
            Mockito.`when`(roomStore!!.getAllFavorite()).thenReturn(dataSourceFactory)

            val allFavorite = roomStore?.getAllFavorite()
            roomStore?.getAllFavorite()
            assertNotNull(allFavorite)
        }
    }
}