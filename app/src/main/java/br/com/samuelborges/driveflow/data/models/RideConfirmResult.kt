package br.com.samuelborges.driveflow.data.models

sealed class RideConfirmResult {
    data class Success(val data: RideConfirmResponse) : RideConfirmResult()
    data class Error(val error: RideConfirmError) : RideConfirmResult()
}