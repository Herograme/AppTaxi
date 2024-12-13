package br.com.samuelborges.driveflow.data.network


import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

data class RideRequest(
    val customer_id: String,
    val origin: String,
    val destination: String,
)

data class RideConfirmRequest(
    val customer_id: String,
    val origin: String,
    val destination: String,
    val distance: Int,
    val duration: String,
    val driver: Driver,
    val value: Number
)

data class Driver(
    val id: Int,
    val name: String,
)

interface ApiService {
    @POST("/ride/estimate")
    suspend fun estimateRide(@Body request: RideRequest): Response<ResponseBody>

    @PATCH("/ride/confirm")
    suspend fun confirmRide(@Body request: RideConfirmRequest): Response<ResponseBody>

    @GET("/ride/{customer_id}")
    suspend fun getRideHistory(
        @Path("customer_id") customerId: String?,
        @Query("driver_id") driverId: Int?
    ): Response<ResponseBody>
}

