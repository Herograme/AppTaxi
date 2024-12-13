package br.com.samuelborges.driveflow.ui.screens

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.samuelborges.driveflow.data.models.Location
import br.com.samuelborges.driveflow.data.models.RideEstimateResponse
import br.com.samuelborges.driveflow.data.models.RideEstimateResult
import br.com.samuelborges.driveflow.data.repository.DirectionsRepository
import br.com.samuelborges.driveflow.data.repository.StaticMapRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class SharedRideViewModel() : ViewModel() {
    private val _rideEstimate = MutableStateFlow<RideEstimateResponse?>(null)
    val rideEstimate: StateFlow<RideEstimateResponse?> = _rideEstimate

    private val _customerID = MutableStateFlow<String?>(null)
    val customerID: StateFlow<String?> = _customerID.asStateFlow()

    private val _origin = MutableStateFlow<String?>(null)
    val origin: StateFlow<String?> = _origin.asStateFlow()

    private val _destination = MutableStateFlow<String?>(null)
    val destination: StateFlow<String?> = _destination.asStateFlow()

    private val _mapImage = MutableStateFlow<Bitmap?>(null)
    val mapImage: StateFlow<Bitmap?> = _mapImage.asStateFlow()

    private val _polyline = MutableStateFlow<String?>(null)
    //val polyline: StateFlow<String?> = _polyline.asStateFlow()

    fun saveRideEstimate(response: RideEstimateResult.Success, apiKey: String) {
        _rideEstimate.value = response.data

        val estimate = response.data

        fetchDirections(estimate.origin, estimate.destination, apiKey)
        fetchMapImage(apiKey)
    }

    fun updateCustomerID(customerID:String){
        _customerID.value = customerID
    }

    fun updateOriginAndDestination(origin: String, destination: String){
        _origin.value = origin
        _destination.value = destination
    }

    private fun fetchDirections(origin: Location, destination: Location, apiKey: String) {
        viewModelScope.launch {
            val result = DirectionsRepository.getDirectionsPolyline(origin, destination, apiKey)
            _polyline.value = result
        }
    }

    private fun fetchMapImage(apiKey: String, width: Int = 600, height: Int = 400) {
        viewModelScope.launch {
            _polyline.value?.let {
                val bitmap =
                    StaticMapRepository.GetStaticMapBitmap(_polyline.value!!, apiKey, width, height)
                bitmap?.let {
                    _mapImage.value = it
                }
            }
        }
    }


}