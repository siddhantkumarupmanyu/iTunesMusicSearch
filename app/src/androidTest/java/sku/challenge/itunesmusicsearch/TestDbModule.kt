package sku.challenge.itunesmusicsearch

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import sku.challenge.itunesmusicsearch.db.TracksDao
import sku.challenge.itunesmusicsearch.db.TracksDb
import sku.challenge.itunesmusicsearch.di.DbModule
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DbModule::class]
)
object TestDbModule {

    @Singleton
    @Provides
    fun providesDb(app: Application): TracksDb {
        return Room.inMemoryDatabaseBuilder(
            app,
            TracksDb::class.java
        ).build()
    }

    @Singleton
    @Provides
    fun providesTrackerDb(db: TracksDb): TracksDao {
        return db.tracksDao()
    }

}