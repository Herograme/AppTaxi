package br.com.samuelborges.driveflow.ui.screens.rideHistory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.samuelborges.driveflow.R
import br.com.samuelborges.driveflow.data.models.RideHistory
import br.com.samuelborges.driveflow.ui.components.DriverFilterSelector
import br.com.samuelborges.driveflow.ui.components.LinedTopBar

data class DriverItem(
    val id: Int?,
    val name: String
)

val drivers = listOf(
    DriverItem(1, "Homer Simpson"),
    DriverItem(2, "Dominic Toretto"),
    DriverItem(3, "James Bond")
)

@Composable
fun RideHistoryScreen(viewModel: RideHistoryViewModel = viewModel()) {
    val titleIcon = painterResource(R.drawable.history_icon)

    val customerId by viewModel.customerId.collectAsState()
    val state by viewModel.historyState.collectAsState()

    Scaffold(
        topBar = {
            LinedTopBar(
                title = "Rides History",
                contentColor = MaterialTheme.colorScheme.primary,
                backgroundColor = MaterialTheme.colorScheme.surface,
                height = 40.dp,
                titleIcon = {
                    Icon(titleIcon , contentDescription = "Icon",
                        modifier = Modifier.size(24.dp)
                    )

                }
            )
        }
    ){ paddingValues ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(paddingValues)
        ) {
            OutlinedTextField(
                value = customerId?: "",
                onValueChange = viewModel::onCustomerIdChanged,
                label = { Text("Customer ID") },
                modifier = Modifier.fillMaxWidth()
            )

            DriverFilterSelector(
                drivers = drivers,
                onSelectionChange = viewModel::fetchHistory,
                modifier = Modifier
            )

            when (state) {
                is RideHistoryState.Loading -> {
                    Text("Loading")
                }

                is RideHistoryState.Success -> {
                    val rides = (state as RideHistoryState.Success).rides
                    RideList(rides = rides)
                }

                is RideHistoryState.Error -> {
                    val errorMessage = (state as RideHistoryState.Error).message
                    Text("Error: $errorMessage")
                }
                RideHistoryState.Null -> {}
            }
        }
    }


}



@Composable
fun RideList(rides: List<RideHistory>) {
    LazyColumn {
        items(rides.size) { index ->
            Card(modifier = Modifier.padding(vertical = 8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Data e Hora: ${rides[index].date}")
                    Text("Motorista: ${rides[index].driver.name}")
                    Text("Origem: ${rides[index].origin}")
                    Text("Destino: ${rides[index].destination}")
                    Text("Distância: ${rides[index].distance} km")
                    Text("Tempo: ${rides[index].duration}")
                    Text("Valor: R$ ${rides[index].value}")
                }
            }
        }
    }
}