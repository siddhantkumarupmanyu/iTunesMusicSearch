package sku.challenge.itunesmusicsearch.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

const val BASE_URL = "https://itunes.apple.com"

@Module
@InstallIn(SingletonComponent::class)
object ConstantsModule {

    @Provides
    fun baseUrl(): BaseUrl {
        return BaseUrl(BASE_URL)
    }

}

data class BaseUrl(val baseUrl: String)