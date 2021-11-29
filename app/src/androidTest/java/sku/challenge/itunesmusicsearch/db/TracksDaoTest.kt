package sku.challenge.itunesmusicsearch.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import sku.challenge.itunesmusicsearch.vo.Track
import sku.challenge.itunesmusicsearch.vo.TrackSearch


// tests should neve extend, every test should be decoupled from other
// ignoring this one
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TracksDaoTest : DbTest() {

    private val tracksDao
        get() = db.tracksDao()

    @Test
    fun insertTrack() = runTest {
        val track = Track(1, "overdrive", "krsna", "https://example.com/thumbnail_over_krsna.jpg")
        val track2 =
            Track(4, "overdrive", "prozpekt", "https://example.com/thumbnail_over_proz.jpg")

        tracksDao.insertTracks(track, track2)

        val retrievedTrack1 = tracksDao.getTrack(1)
        val retrievedTrack2 = tracksDao.getTrack(4)

        assertThat(retrievedTrack1, `is`(track))
        assertThat(retrievedTrack2, `is`(track2))
    }

    @Test
    fun insertTrackSearch() = runBlocking {
        val tracks = listOf(
            Track(1, "overdrive", "krsna", "https://example.com/thumbnail_over_krsna.jpg"),
            Track(4, "overdrive", "prozpekt", "https://example.com/thumbnail_over_proz.jpg")
        )

        val trackSearch = TrackSearch(tracks.size, tracks).apply {
            query = "overdrive"
        }

        tracksDao.insertTrackerSearch(trackSearch)

        val retrievedResult = tracksDao.queryTrackSearch("overdrive")
        assertThat(retrievedResult, `is`(trackSearch))
    }

    @Ignore
    @Test
    fun noTrackSearchFound() {
        // room return null if no dataIsFound

        // val retrievedResult = tracksDao.queryTrackSearch("overdrive")
        //
        // assertThat(retrievedResult, `is`(nullValue()))
    }
}
