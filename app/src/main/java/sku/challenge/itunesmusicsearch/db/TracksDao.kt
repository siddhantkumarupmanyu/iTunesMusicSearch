package sku.challenge.itunesmusicsearch.db

import androidx.room.*
import sku.challenge.itunesmusicsearch.vo.Track
import sku.challenge.itunesmusicsearch.vo.TrackSearch
import kotlin.math.max


@Dao
abstract class TracksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertTracks(vararg track: Track)

    @Query("SELECT * FROM track WHERE trackId = :trackId")
    abstract suspend fun getTrack(trackId: Long): Track

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertTrackSearchRoomEntity(vararg trackSearch: TrackSearchRoomEntity)


    suspend fun insertTrackerSearch(trackSearch: TrackSearch) {
        val trackSearchRoomEntity = TrackSearchRoomEntity(
            trackSearch.query,
            TrackSearchRoomEntity.getResultStringFromResultList(trackSearch.results),
            trackSearch.resultCount
        )

        return insertTrackSearchRoomEntity(trackSearchRoomEntity)
    }


    // @Transaction
    // suspend fun queryTrackSearch(query: String): TrackSearch {
    //     TODO()
    // }

    @Entity
    data class TrackSearchRoomEntity(
        @PrimaryKey
        val query: String,
        val results: String,
        val count: Int
    ) {

        companion object {
            fun getResultStringFromResultList(results: List<Track>): String {
                val string = StringBuilder()
                results.forEach {
                    string.append("${it.trackId}, ")
                }
                string.setLength(max(string.length - 2, 0))
                return string.toString()
            }

        }

    }

}