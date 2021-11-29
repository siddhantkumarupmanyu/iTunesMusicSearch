package sku.challenge.itunesmusicsearch.repository

import sku.challenge.itunesmusicsearch.api.ApiService
import sku.challenge.itunesmusicsearch.db.TracksDao

// lack of better name
class SearchRepositoryImpl(
    private val apiService: ApiService,
    private val dao: TracksDao,
    private val isInternetAvailable: () -> Boolean
) {



}