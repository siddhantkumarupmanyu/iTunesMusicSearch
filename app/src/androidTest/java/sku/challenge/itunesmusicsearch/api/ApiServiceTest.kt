package sku.challenge.itunesmusicsearch.api

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sku.challenge.itunesmusicsearch.test_utils.enqueueResponse
import sku.challenge.itunesmusicsearch.vo.Track
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@SmallTest
@RunWith(AndroidJUnit4::class)
class ApiServiceTest {

    private val mockWebServer = MockWebServer()

    private lateinit var service: ApiService

    @Before
    fun setUp() {
        mockWebServer.start(8080)

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun searchSong() = runTest {
        mockWebServer.enqueueResponse("overdrive.txt")

        val trackSearch = service.searchTracks("overdrive")

        val request = mockWebServer.takeRequest(2, TimeUnit.SECONDS)
        assertThat(request.path, `is`("/search?term=overdrive&media=music&entity=song"))

        assertThat(trackSearch.resultCount, `is`(50))

        val tracks = trackSearch.results


        assertTrack(
            tracks[0],
            "Calvin Harris & Ummet Ozcan",
            922876190,
            "Overdrive",
            "https://is4-ssl.mzstatic.com/image/thumb/Music125/v4/c9/18/e4/c918e4dd-f99a-f226-67d2-54ef9b59b9fc/source/100x100bb.jpg"
        )

        assertTrack(
            tracks[10],
            "Overdrive",
            438520905,
            "Pump To Hard (DJ Activator Remix)",
            "https://is5-ssl.mzstatic.com/image/thumb/Music/v4/24/4d/86/244d868e-9690-e536-7a8a-5f075d666aba/source/100x100bb.jpg"
        )

    }

    private fun assertTrack(
        track: Track,
        artistName: String,
        trackId: Long,
        trackName: String,
        thumbnailUrl: String
    ) {
        assertThat(track.artistName, `is`(artistName))
        assertThat(track.trackId, `is`(trackId))
        assertThat(track.trackName, `is`(trackName))
        assertThat(track.thumbnailUrl, `is`(thumbnailUrl))
    }

}