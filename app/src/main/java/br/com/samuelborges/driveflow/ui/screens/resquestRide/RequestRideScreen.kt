package br.com.samuelborges.driveflow.ui.screens.resquestRide

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.samuelborges.driveflow.R
import br.com.samuelborges.driveflow.data.models.RideEstimateResult
import br.com.samuelborges.driveflow.ui.components.CustomTopBar
import br.com.samuelborges.driveflow.ui.components.LinedTopBar
import br.com.samuelborges.driveflow.ui.navigation.NavRoutes
import br.com.samuelborges.driveflow.ui.theme.AppTheme
import kotlinx.coroutines.delay


@Composable
fun RequestRideScreen(viewModel: RideViewModel = viewModel(),navController: NavController ) {
    val titleIcon = painterResource(R.drawable.van)

    val customerId = viewModel.customerId.collectAsState().value
    val origin = viewModel.origin.collectAsState().value
    val destination = viewModel.destination.collectAsState().value
    val uiState = viewModel.uiState.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value

    val buttonColors = ButtonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
        disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
    )

    AppTheme {
        Scaffold(
            topBar = {
                LinedTopBar(
                    title = "Rides",
                    contentColor = MaterialTheme.colorScheme.primary,
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    height = 40.dp,
                    titleIcon = {
                        Image(titleIcon , contentDescription = "Icon",
                        modifier = Modifier.size(24.dp)
                        )

                    }
                    )



            }
        ) { paddingValues ->
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = customerId,
                        onValueChange = viewModel::setCustomerId,
                        label = { Text("Customer ID") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = origin,
                        onValueChange = viewModel::setOrigin,
                        label = { Text("Origem") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = destination,
                        onValueChange = viewModel::setDestination,
                        label = { Text("Destino") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    ElevatedButton(
                        onClick = viewModel::estimateRide,
                        colors = buttonColors,
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        Text("Estimar Corrida")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    when (uiState) {
                        is RideEstimateResult.Success -> {
                            Text("Estimativa recebida com sucesso!", color = MaterialTheme.colorScheme.primary)
                            LaunchedEffect(uiState){
                                delay(2*1000)
                                navController.navigate(NavRoutes.RideOptions.route)

                            }
                        }
                        is RideEstimateResult.Error -> {
                            val error = (uiState as RideEstimateResult.Error).error
                            Text("Erro: ${error.error_code} - ${error.error_description}", color = MaterialTheme.colorScheme.error)
                        }
                        null -> {}
                    }
                }
            }
        }
    }
}

