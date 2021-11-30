package sku.challenge.itunesmusicsearch

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class Network(
    baseUrl: String,
    private val client: OkHttpClient
) {

    private val pingUrl = "$baseUrl/search"

    suspend fun isInternetConnectionAvailable() = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url(pingUrl)
            .head()
            .build()

        val call = client.newCall(request)

        try {
            val response = call.execute()

            // can use fail fast here
            // for now assert will work
            assert(response.code() == 200)
            response.close()

            return@withContext true
        } catch (e: IOException) { // assuming every IO exception is Network Exception
            return@withContext false
        }

    }

}