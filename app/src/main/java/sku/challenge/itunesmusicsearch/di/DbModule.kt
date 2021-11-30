package sku.challenge.itunesmusicsearch.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sku.challenge.itunesmusicsearch.db.TracksDao
import sku.challenge.itunesmusicsearch.db.TracksDb
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DbModule {

    @Singleton
    @Provides
    fun providesDb(app: Application): TracksDb {
        return Room.databaseBuilder(app, TracksDb::class.java, "tracks.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesTrackerDao(db: TracksDb): TracksDao {
        return db.tracksDao()
    }

}