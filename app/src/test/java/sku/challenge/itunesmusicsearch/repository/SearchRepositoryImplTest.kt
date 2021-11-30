package sku.challenge.itunesmusicsearch.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import sku.challenge.itunesmusicsearch.api.ApiService
import sku.challenge.itunesmusicsearch.db.TracksDao
import sku.challenge.itunesmusicsearch.test_utils.mock
import sku.challenge.itunesmusicsearch.vo.Track
import sku.challenge.itunesmusicsearch.vo.TrackSearch

@ExperimentalCoroutinesApi
class SearchRepositoryImplTest {

    private val tracks = listOf(
        Track(1, "overdrive", "krsna", "https://example.com/thumbnail_over_krsna.jpg"),
        Track(4, "overdrive", "prozpekt", "https://example.com/thumbnail_over_proz.jpg")
    )
    private val trackSearch = TrackSearch(
        2,
        tracks
    )

    private val apiService = mock<ApiService>()
    private val tracksDao = mock<TracksDao>()

    @Test
    fun searchTrackAndSaveItToRoom() = runTest {
        `when`(apiService.searchTracks("overdrive")).thenReturn(trackSearch)

        val repository = SearchRepositoryImpl(apiService, tracksDao) { true }

        val result = repository.query("overdrive")

        assertThat(result, `is`(tracks))

        verify(apiService).searchTracks("overdrive")

        verify(tracksDao).insertTrackSearch(trackSearch.copy().apply {
            query = "overdrive"
        })

        verify(tracksDao, never()).queryTrackSearch(anyString())
    }

    @Test
    fun loadFromRoom_WhenInternetIsNotAvailable() = runTest {
        `when`(tracksDao.queryTrackSearch("overdrive")).thenReturn(trackSearch)

        val repository = SearchRepositoryImpl(apiService, tracksDao) { false }

        val result = repository.query("overdrive")
        assertThat(result, `is`(tracks))

        verify(tracksDao).queryTrackSearch("overdrive")

        verify(apiService, never()).searchTracks(anyString(), anyString(), anyString())
        verify(tracksDao, never()).insertTrackSearch(any(TrackSearch::class.java))
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}