package sku.challenge.itunesmusicsearch.vo

import com.google.gson.annotations.SerializedName

data class Track(
    val trackId: Long,
    val trackName: String,
    val artistName: String,
    @field:SerializedName("artworkUrl100")
    val thumbnailUrl: String
)