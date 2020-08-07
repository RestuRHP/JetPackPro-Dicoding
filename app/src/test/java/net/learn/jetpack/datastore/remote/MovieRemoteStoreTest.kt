package net.learn.jetpack.datastore.remote

import kotlinx.coroutines.runBlocking
import net.learn.jetpack.datastore.MovieStore.MovieRemoteStore
import net.learn.jetpack.utils.FileReader
import net.learn.jetpack.utils.MockTestUtil
import net.learn.jetpack.utils.service.Api
import net.learn.jetpack.utils.service.Retrofit
import net.learn.submission4mvvm.model.movies.Movie
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class MovieRemoteStoreTest {

    private val server: MockWebServer = MockWebServer()
    private val MOCK_WEBSERVER_PORT = 8080
    lateinit var api: Api
    lateinit var remoteStore: MovieRemoteStore
    var movieSet = mutableListOf<Movie>()

    @Before
    fun init() {
        server.start(MOCK_WEBSERVER_PORT)
        api = retrofit2.Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
        remoteStore = MovieRemoteStore(api)
    }

    @After
    fun shutdown() {
        server.shutdown()
    }

    @Test
    fun parsingApi() {
        server.apply {
            enqueue(MockResponse().setBody(FileReader("tmdb_movie.json").content))
        }
        runBlocking {
            val response = remoteStore.getSets(1)
            Assert.assertNotNull(response)
        }
    }

    @Test
    fun getDataApi() {
        val api = Retrofit.API
        runBlocking {
            movieSet.addAll(MockTestUtil.mockMovieList())
            val response = api.getMovies(page = 1)
            assert(response.isSuccessful)
            movieSet = response.body()!!.results
            Assert.assertNotNull(movieSet)
        }
    }
}