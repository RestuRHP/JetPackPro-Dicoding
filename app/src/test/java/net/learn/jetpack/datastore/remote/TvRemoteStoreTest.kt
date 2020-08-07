package net.learn.jetpack.datastore.remote

import kotlinx.coroutines.runBlocking
import net.learn.jetpack.model.tv.TvShow
import net.learn.jetpack.utils.service.Retrofit
import org.junit.Assert.assertNotNull
import org.junit.Test

class TvRemoteStoreTest {

    var movieSet = mutableListOf<TvShow>()

    @Test
    fun getDataApi() {
        val api = Retrofit.API
        runBlocking {
            val response = api.getTv(page = 1)
            assert(response.isSuccessful)
            movieSet = response.body()!!.results
            assertNotNull(movieSet)
        }
    }
}