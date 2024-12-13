package br.com.samuelborges.driveflow.data.models

sealed class RideEstimateResult {
    data class Success(val data: RideEstimateResponse) : RideEstimateResult()
    data class Error(val error: RideEstimateErrorResponse) : RideEstimateResult()
}