package br.com.samuelborges.driveflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.samuelborges.driveflow.ui.navigation.NavRoutes
import br.com.samuelborges.driveflow.ui.screens.SharedRideViewModel
//import androidx.compose.ui.unit.dp
import br.com.samuelborges.driveflow.ui.screens.resquestRide.RequestRideScreen
import br.com.samuelborges.driveflow.ui.screens.resquestRide.RideViewModel
import br.com.samuelborges.driveflow.ui.screens.rideHistory.RideHistoryScreen
import br.com.samuelborges.driveflow.ui.screens.rideOptions.RideOptionsScreen
import br.com.samuelborges.driveflow.ui.screens.rideOptions.RideOptionsViewModel
import br.com.samuelborges.driveflow.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                TaxiApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    AppTheme {
        //RideOptionsScreen()
        RideHistoryScreen()
    }
}

@Composable
fun TaxiApp(){
    val API_KEY = "AIzaSyAOVYRIgupAurZup5y1PRh8Ismb1A3lLao"
    val navController = rememberNavController()
    val sharedRideViewModel: SharedRideViewModel = viewModel()
    val rideViewModel = RideViewModel(sharedRideViewModel,API_KEY)
    val rideOptionsViewModel = RideOptionsViewModel(sharedRideViewModel)


    NavHost(navController = navController, startDestination = NavRoutes.RideHistory.route){
        composable(NavRoutes.RideRequest.route) {
            RequestRideScreen(viewModel = rideViewModel,navController)
        }

        composable(NavRoutes.RideOptions.route) {
            RideOptionsScreen(rideOptionsViewModel,navController)
        }

        composable(NavRoutes.RideHistory.route) {
            RideHistoryScreen()
        }

    }
}

