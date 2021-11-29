package sku.challenge.itunesmusicsearch.db

import androidx.room.Database
import androidx.room.RoomDatabase
import sku.challenge.itunesmusicsearch.vo.Track

@Database(
    entities = [TracksDao.TrackSearchRoomEntity::class, Track::class],
    version = 1,
    exportSchema = false
)
abstract class TracksDb : RoomDatabase() {

    abstract fun tracksDao(): TracksDao

}