package br.com.samuelborges.driveflow.ui.screens.resquestRide

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.samuelborges.driveflow.data.models.RideEstimateErrorResponse
import br.com.samuelborges.driveflow.data.models.RideEstimateResponse
import br.com.samuelborges.driveflow.data.models.RideEstimateResult
import br.com.samuelborges.driveflow.data.network.RetrofitIntance
import br.com.samuelborges.driveflow.data.network.RideRequest
import br.com.samuelborges.driveflow.ui.screens.SharedRideViewModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RideViewModel(
    private val sharedRideViewModel: SharedRideViewModel,
    private val apiKey: String
) : ViewModel() {

    private val _customerId = MutableStateFlow("")
    val customerId: StateFlow<String> = _customerId.asStateFlow()

    private val _origin = MutableStateFlow("")
    val origin: StateFlow<String> = _origin.asStateFlow()

    private val _destination = MutableStateFlow("")
    val destination: StateFlow<String> = _destination.asStateFlow()

    private val _uiState = MutableStateFlow<RideEstimateResult?>(null)
    val uiState: StateFlow<RideEstimateResult?> = _uiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun setCustomerId(value: String) {
        _customerId.value = value
    }

    fun setOrigin(value: String) {
        _origin.value = value
    }

    fun setDestination(value: String) {
        _destination.value = value
    }

    fun estimateRide() {
        viewModelScope.launch {
            _isLoading.value = true
            _uiState.value = null
            val request = RideRequest(_customerId.value, _origin.value, _destination.value)
            try {
                val response = RetrofitIntance.api.estimateRide(request)
                if (response.isSuccessful) {
                    val responseBody = response.body()?.string()
                    val rideEstimateResponse =
                        Gson().fromJson(responseBody, RideEstimateResponse::class.java)

                    if (rideEstimateResponse != null) {
                        _uiState.value = RideEstimateResult.Success(rideEstimateResponse)
                        sharedRideViewModel.saveRideEstimate(
                            RideEstimateResult.Success(
                                rideEstimateResponse
                            ), apiKey
                        )
                    } else {
                        _uiState.value = RideEstimateResult.Error(
                            RideEstimateErrorResponse(
                                "UNKNOWN_ERROR",
                                "Resposta inesperada do servidor."
                            )
                        )
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorResponse =
                        Gson().fromJson(errorBody, RideEstimateErrorResponse::class.java)
                    _uiState.value = RideEstimateResult.Error(
                        errorResponse ?: RideEstimateErrorResponse(
                            "UNKNOWN_ERROR",
                            "Erro desconhecido"
                        )
                    )
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse =
                    Gson().fromJson(errorBody, RideEstimateErrorResponse::class.java)
                _uiState.value = RideEstimateResult.Error(
                    errorResponse ?: RideEstimateErrorResponse(
                        "UNKNOWN_ERROR",
                        "Erro desconhecido"
                    )
                )
            } catch (e: Exception) {
                _uiState.value = RideEstimateResult.Error(
                    RideEstimateErrorResponse(
                        "NETWORK_ERROR", "Erro de rede: ${e.message}"
                    )
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
}