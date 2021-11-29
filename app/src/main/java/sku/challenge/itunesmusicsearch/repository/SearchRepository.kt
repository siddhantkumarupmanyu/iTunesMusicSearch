package sku.challenge.itunesmusicsearch.repository

import sku.challenge.itunesmusicsearch.vo.Track

interface SearchRepository {

    suspend fun query(query: String): List<Track>
}