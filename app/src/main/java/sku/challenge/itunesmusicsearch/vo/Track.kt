package sku.challenge.itunesmusicsearch.vo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Track(
    @PrimaryKey
    val trackId: Long,
    val trackName: String,
    val artistName: String,
    @field:SerializedName("artworkUrl100")
    val thumbnailUrl: String
)