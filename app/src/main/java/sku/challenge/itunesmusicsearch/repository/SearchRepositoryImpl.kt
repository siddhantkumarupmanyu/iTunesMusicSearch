package sku.challenge.itunesmusicsearch.repository

import sku.challenge.itunesmusicsearch.api.ApiService
import sku.challenge.itunesmusicsearch.db.TracksDao
import sku.challenge.itunesmusicsearch.vo.Track
import javax.inject.Inject

// lack of better name
class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: TracksDao,
    private val isInternetAvailable: () -> Boolean
) : SearchRepository {

    override suspend fun query(query: String): List<Track> {
        return if (isInternetAvailable()) {
            val trackSearch = apiService.searchTracks(query)
            dao.insertTrackSearch(trackSearch)

            trackSearch.results
        } else {
            val trackSearch = dao.queryTrackSearch(query)

            trackSearch.results
        }
    }


}