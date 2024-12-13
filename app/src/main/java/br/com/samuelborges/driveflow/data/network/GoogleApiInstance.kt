package br.com.samuelborges.driveflow.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GoogleApiInstance {

    private const val BASE_URL = "https://maps.googleapis.com/maps/api/"

    val directionsApi : DirectionsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DirectionsApiService::class.java)
    }

    val staticMapApi : StaticMapApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build()
            .create(StaticMapApiService::class.java)

    }
}