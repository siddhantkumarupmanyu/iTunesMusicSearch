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

    @Query("SELECT * FROM track WHERE trackId IN(:tracksIds)")
    abstract suspend fun getTracks(tracksIds: List<Long>): List<Track>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertTrackSearchRoomEntity(vararg trackSearchRoomEntity: TrackSearchRoomEntity)


    suspend fun insertTrackerSearch(trackSearch: TrackSearch) {
        val trackSearchRoomEntity = TrackSearchRoomEntity(
            trackSearch.query,
            TrackSearchRoomEntity.getResultStringFromResultList(trackSearch.results),
            trackSearch.resultCount
        )

        insertTracks(*trackSearch.results.toTypedArray())

        return insertTrackSearchRoomEntity(trackSearchRoomEntity)
    }

    @Query("SELECT * FROM tracksearchroomentity WHERE `query` = :query")
    abstract suspend fun getTrackSearchRoomEntity(query: String): TrackSearchRoomEntity?

    // not going to care about order for now
    @Transaction
    open suspend fun queryTrackSearch(query: String): TrackSearch {
        val trackerSearchRoomEntity =
            getTrackSearchRoomEntity(query) ?: return TrackSearch.EMPTY_TRACKER_SEARCH

        val longList = trackerSearchRoomEntity.resultsLongList()

        val tracks = getTracks(longList)
        return TrackSearch(trackerSearchRoomEntity.count, tracks)
    }

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

        fun resultsLongList(): List<Long> {
            return results.split(",").toList().map {
                it.trim().toLong()
            }
        }

    }

}