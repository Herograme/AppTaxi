package br.com.samuelborges.driveflow.data.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StaticMapApiService {
    @GET("staticmap")
    suspend fun getStaticMap(
        @Query("size") size: String,
        @Query("path") path: String,
        @Query("key") apiKey: String,
    ): Response<ResponseBody>
}