package br.com.samuelborges.driveflow.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import br.com.samuelborges.driveflow.data.network.GoogleApiInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object StaticMapRepository {
    suspend fun GetStaticMapBitmap(
        encodedPolyline: String,
        apiKey: String,
        width: Int = 600,
        height: Int = 400
    ):Bitmap? {
        val size = "${width}x${height}"
        val path = "enc:$encodedPolyline"

        return withContext(Dispatchers.IO) {
            try {
                Log.d("DirectionsRepository","Fetching map")
                val response = GoogleApiInstance.staticMapApi.getStaticMap(size, path, apiKey)
                if (response.isSuccessful) {
                    response.body()?.byteStream()?.let {
                        BitmapFactory.decodeStream(it)
                    }
                } else {
                    Log.e("DirectionsRepository", "Error fetching directions: ${response.code()}")
                    Log.e("DirectionsRepository", "Error fetching directions: ${response.errorBody()}")
                    null
                }


            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}