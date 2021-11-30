package sku.challenge.itunesmusicsearch

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class NetworkTest {


    private val mockWebServer = MockWebServer()

    @Test
    fun internetIsAvailable() = runTest {
        mockWebServer.start(8080)

        val okHttpClient = OkHttpClient()
        val network = Network(mockWebServer.url("/").toString(), okHttpClient)

        val mockResponse = MockResponse()
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        val internetStatus = network.isInternetConnectionAvailable()

        val request = mockWebServer.takeRequest(2, TimeUnit.SECONDS)
        assertThat(request.method, `is`("HEAD"))

        assertTrue(internetStatus)

        mockWebServer.shutdown()
    }

    @Test
    fun internetIsUnavailable() = runTest {
        mockWebServer.start(8080)

        val okHttpClient = OkHttpClient()
        val network = Network(mockWebServer.url("/").toString(), okHttpClient)

        mockWebServer.shutdown()

        val internetStatus = network.isInternetConnectionAvailable()

        assertFalse(internetStatus)
    }

}