package sku.challenge.itunesmusicsearch

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class Network(
    private val baseUrl: String,
    private val client: OkHttpClient
) {

    private val pingUrl = "$baseUrl/search"

    suspend fun isInternetConnectionAvailable() = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url(pingUrl)
            .head()
            .build()

        val call = client.newCall(request)
        val response = call.execute()

        // can use fail fast here
        // for now assert will work
        assert(response.code() == 200)
        return@withContext true
    }

}