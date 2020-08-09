package net.learn.jetpack.datastore.remote

import kotlinx.coroutines.runBlocking
import net.learn.jetpack.service.Api
import org.hamcrest.core.Is.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TvRemoteStoreTest : ApiAbstract<Api>() {
    private lateinit var service: Api

    @Before
    fun iniService() {
        this.service = createService(Api::class.java)
    }

    @Test
    fun getData() {
        enqueueResponse("tmdb_tv.json")
        runBlocking {
            val response = service.getTv(page = 1)
            Assert.assertNotNull(response.body()?.results)
            Assert.assertThat(response.body()?.results?.get(0)?.id, `is`(2734))
            Assert.assertThat(
                response.body()?.results?.get(0)?.title, `is`("Law & Order: Special Victims Unit")
            )
        }
    }
}