package net.learn.jetpack.data.datastore.remote

import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import net.learn.jetpack.data.network.Api
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
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
    fun `given remote store when get discovery movie from API should return success`() {
        enqueueResponse("tmdb_movie.json")
        runBlocking {
            val response = service.getDiscoveryMovie(1)
            assertNotNull(response.body()?.results)
            assertThat(response.body()?.results?.get(0)?.id, `is`(583083))
            assertThat(response.body()?.results?.get(0)?.title, `is`("The Kissing Booth 2"))
        }
    }
}