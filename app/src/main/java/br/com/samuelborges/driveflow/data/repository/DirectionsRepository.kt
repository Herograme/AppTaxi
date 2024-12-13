package br.com.samuelborges.driveflow.data.repository

import android.util.Log
import android.webkit.WebStorage.Origin
import br.com.samuelborges.driveflow.data.models.Location
import br.com.samuelborges.driveflow.data.network.GoogleApiInstance
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

object DirectionsRepository {
    suspend fun getDirectionsPolyline(
        origin:Location,
        destination: Location,
        apiKey: String
    ):String?{
      val originFormatted = "${origin.latitude},${origin.longitude}"
      val destinationFormatted = "${destination.latitude},${destination.longitude}"

      return withContext(Dispatchers.IO){
          try {
              Log.d("DirectionsRepository","Fetching directions polyline from $originFormatted to $destinationFormatted")
              val response = GoogleApiInstance.directionsApi.getDirections(originFormatted,destinationFormatted,apiKey)
              Log.d("DirectionsRepository","Response: $response")
              if (response.isSuccessful){
                  response.body()?.routes?.firstOrNull()?.overview_polyline?.points
              }else{
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