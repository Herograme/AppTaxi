package br.com.samuelborges.driveflow.ui.screens.rideHistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.samuelborges.driveflow.data.models.customerHistory
import br.com.samuelborges.driveflow.data.network.RetrofitIntance
import br.com.samuelborges.driveflow.ui.screens.SharedRideViewModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RideHistoryViewModel():ViewModel() {

    private val _customerId = MutableStateFlow<String?>(null)
    val customerId: StateFlow<String?> = _customerId.asStateFlow()

    private val _historyState = MutableStateFlow<RideHistoryState>(RideHistoryState.Null)
    val historyState: StateFlow<RideHistoryState> = _historyState.asStateFlow()

    fun onCustomerIdChanged(content:String){
        _customerId.value  = content
    }


    fun fetchHistory(driverInfo:DriverItem){
        viewModelScope.launch {
            _historyState.value = RideHistoryState.Loading
            try {
                val result  = RetrofitIntance.api.getRideHistory(customerId.value,driverInfo.id)

                if (result.isSuccessful){
                    val responseBody = result.body()?.string()
                    val rideHistoryResponse = Gson().fromJson(responseBody, customerHistory::class.java)

                    if (rideHistoryResponse != null){
                        _historyState.value = RideHistoryState.Success(rideHistoryResponse.rides)
                    } else {
                        _historyState.value = RideHistoryState.Error("Resposta inesperada do servidor.")
                    }
                } else {
                    _historyState.value = RideHistoryState.Error("Erro desconhecido")
                }
            } catch (ex: HttpException){
                val errorBody = ex.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, RideHistoryState.Error::class.java)
                _historyState.value = errorResponse ?: RideHistoryState.Error("Erro desconhecido")
            } catch (e: Exception){
                _historyState.value = RideHistoryState.Error("Erro de rede: ${e.message}")
            }
        }
    }
}