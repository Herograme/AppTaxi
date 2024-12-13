package br.com.samuelborges.driveflow.ui.navigation



sealed class NavRoutes(val route: String) {
    data object RideRequest : NavRoutes("ride_request")
    data object RideOptions : NavRoutes("ride_options")
    data object RideHistory : NavRoutes("ride_history")
}

