package sku.challenge.itunesmusicsearch.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import sku.challenge.itunesmusicsearch.InternetChecker
import sku.challenge.itunesmusicsearch.api.ApiService
import sku.challenge.itunesmusicsearch.db.TracksDao
import sku.challenge.itunesmusicsearch.repository.SearchRepository
import sku.challenge.itunesmusicsearch.repository.SearchRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideRepository(
        baseUrl: BaseUrl,
        client: OkHttpClient,
        service: ApiService,
        dao: TracksDao
    ): SearchRepository {
        val checker = InternetChecker(baseUrl.baseUrl, client)
        return SearchRepositoryImpl(
            service,
            dao,
            checker::isInternetConnectionAvailable
        )
    }


}