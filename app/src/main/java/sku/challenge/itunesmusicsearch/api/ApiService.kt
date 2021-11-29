package sku.challenge.itunesmusicsearch.api

import retrofit2.http.GET
import retrofit2.http.Query
import sku.challenge.itunesmusicsearch.vo.TrackSearch

interface ApiService {

    @GET("/search")
    suspend fun searchTracks(
        @Query("term") query: String,
        @Query("media") music: String = "music",
        @Query("entity") entity: String = "song"
    ): TrackSearch

}