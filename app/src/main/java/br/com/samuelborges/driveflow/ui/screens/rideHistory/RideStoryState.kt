package br.com.samuelborges.driveflow.ui.screens.rideHistory

import br.com.samuelborges.driveflow.data.models.RideHistory


sealed class RideHistoryState {
    data object Loading : RideHistoryState()
    data class Success(val rides: List<RideHistory>) : RideHistoryState()
    data class Error(val message: String) : RideHistoryState()
    data object Null : RideHistoryState()
}