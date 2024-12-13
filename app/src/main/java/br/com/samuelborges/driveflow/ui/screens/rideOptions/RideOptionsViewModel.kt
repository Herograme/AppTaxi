package br.com.samuelborges.driveflow.ui.screens.rideOptions

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.samuelborges.driveflow.data.models.RideConfirmError
import br.com.samuelborges.driveflow.data.models.RideConfirmResponse
import br.com.samuelborges.driveflow.data.models.RideConfirmResult
import br.com.samuelborges.driveflow.data.models.RideEstimateResponse
import br.com.samuelborges.driveflow.data.models.RideOption
import br.com.samuelborges.driveflow.data.network.Driver
import br.com.samuelborges.driveflow.data.network.RetrofitIntance
import br.com.samuelborges.driveflow.data.network.RideConfirmRequest
import br.com.samuelborges.driveflow.ui.screens.SharedRideViewModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RideOptionsViewModel(sharedRideViewModel: SharedRideViewModel): ViewModel() {
    
    private var customerId: String? = null
    private var origin: String? = null
    private var destination: String? = null

    private val _rideOptions = MutableStateFlow<RideEstimateResponse?>(null)
    val estimateRide: StateFlow<RideEstimateResponse?> = _rideOptions.asStateFlow()

    private val _bitmap = MutableStateFlow<Bitmap?>(null)
    val bitmap: StateFlow<Bitmap?> = _bitmap.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessages = MutableStateFlow<String?>(null)
    val errorMessages: StateFlow<String?> = _errorMessages.asStateFlow()
    
    private val _response = MutableStateFlow<RideConfirmResult?>(null)
    val response: StateFlow<RideConfirmResult?> = _response.asStateFlow()

    init {
        sharedRideViewModel.rideEstimate.onEach { estimate ->
            estimate?.let {
                _rideOptions.value = it
            }
        }.launchIn(viewModelScope)

        sharedRideViewModel.mapImage.onEach {
            _bitmap.value = it
        }.launchIn(viewModelScope)

        sharedRideViewModel.customerID.onEach {
            customerId = it
        }.launchIn(viewModelScope)

        sharedRideViewModel.origin.onEach {
            origin = it
        }.launchIn(viewModelScope)

        sharedRideViewModel.destination.onEach {
            destination = it
        }.launchIn(viewModelScope)
    }

    fun confirmChoice(rideResult: RideEstimateResponse, option: RideOption) {
        _isLoading.value = true
        _errorMessages.value = null
        
        if(this.customerId == null){
            _errorMessages.value = "Customer ID is null"
            return
        }

        if(this.origin == null){
            _errorMessages.value = "Origin is null"
            return
        }

        if(this.destination == null){
            _errorMessages.value = "Destination is null"
            return
        }

        val rideRequest = RideConfirmRequest(
            customer_id = this.customerId!!,
            origin = this.origin!!,
            destination = this.destination!!,
            distance = rideResult.distance,
            duration = rideResult.duration,
            driver = Driver(
                id = option.id,
                name = option.name
            ),
            value = option.value
        )
        
        viewModelScope.launch {
            try {
                val result = RetrofitIntance.api.confirmRide(rideRequest)
                if (result.isSuccessful) {
                    val responseBody = result.body()?.string()
                    val rideConfirmResponse = Gson().fromJson(responseBody, RideConfirmResponse::class.java)

                    if (rideConfirmResponse != null) {
                        _response.value = RideConfirmResult.Success(rideConfirmResponse)
                    } else {
                        _response.value = RideConfirmResult.Error(RideConfirmError("UNKNOWN_ERROR", "Resposta inesperada do servidor."))
                    }
                } else {
                    _response.value = RideConfirmResult.Error(RideConfirmError("UNKNOWN_ERROR", "Erro desconhecido"))
                }
            } catch (e:HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, RideConfirmError::class.java)

                _response.value = RideConfirmResult.Error(errorResponse ?: RideConfirmError("UNKNOWN_ERROR", "Erro desconhecido"))
            } catch (e: Exception) {
                _response.value = RideConfirmResult.Error(RideConfirmError("NETWORK_ERROR", "Erro de rede: ${e.message}"))
            } finally {
                _isLoading.value = false
            }
            
        }
    }
    
   

}