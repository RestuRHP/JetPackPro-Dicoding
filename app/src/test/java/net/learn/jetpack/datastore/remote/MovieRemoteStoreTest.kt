package net.learn.jetpack.datastore.remote

import kotlinx.coroutines.runBlocking
import net.learn.jetpack.service.Api
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieRemoteStoreTest : ApiAbstract<Api>() {
    private lateinit var service: Api

    @Before
    fun iniService() {
        this.service = createService(Api::class.java)
    }

    @Test
    fun getData() {
        enqueueResponse("tmdb_movie.json")
        runBlocking {
            val response = service.getMovies(page = 1)
            assertNotNull(response.body()?.results)
            assertThat(response.body()?.results?.get(0)?.id, `is`(583083))
            assertThat(response.body()?.results?.get(0)?.title, `is`("The Kissing Booth 2"))
        }
    }
}