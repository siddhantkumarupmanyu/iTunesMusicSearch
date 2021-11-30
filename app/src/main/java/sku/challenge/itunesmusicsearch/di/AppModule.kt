package sku.challenge.itunesmusicsearch.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sku.challenge.itunesmusicsearch.repository.SearchRepository
import sku.challenge.itunesmusicsearch.repository.SearchRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface AppModule {

    @Singleton
    @Binds
    fun provideRepository(repository: SearchRepositoryImpl): SearchRepository


}