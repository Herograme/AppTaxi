package br.com.samuelborges.driveflow.ui.screens.rideOptions

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import br.com.samuelborges.driveflow.R
import br.com.samuelborges.driveflow.data.models.Review
import br.com.samuelborges.driveflow.data.models.RideOption
import br.com.samuelborges.driveflow.ui.components.LinedTopBar
import br.com.samuelborges.driveflow.ui.components.MapComponent



val rideOptions = listOf(
    RideOption(
        id = 1,
        name = "João Silva",
        description = "Motorista experiente",
        vehicle = "Toyota Corolla - Preto",
        review = Review(rating = 4.8, comment = "Muito educado e pontual"),
        value = 25.50
    ), RideOption(
        id = 2,
        name = "Maria Souza",
        description = "Carro confortável",
        vehicle = "Honda Civic - Branco",
        review = Review(rating = 4.5, comment = "Carro limpo e confortável"),
        value = 30.00
    ), RideOption(
        id = 3,
        name = "Carlos Pereira",
        description = "Conhece bem a região",
        vehicle = "Volkswagen Jetta - Azul",
        review = Review(rating = 4.9, comment = "Dirige com segurança"),
        value = 27.75
    )
)


@Composable
fun RideOptionsScreen(
    rideOptionsViewModel: RideOptionsViewModel = viewModel(),
    navController: NavHostController
) {
    val titleIcon = painterResource(R.drawable.van)
    val isLoading by rideOptionsViewModel.isLoading.collectAsState()
    val rideEstimate by rideOptionsViewModel.estimateRide.collectAsState()




    Scaffold(topBar = {
        LinedTopBar(title = "Rides",
            contentColor = MaterialTheme.colorScheme.primary,
            backgroundColor = MaterialTheme.colorScheme.surface,
            height = 40.dp,
            titleIcon = {
                Image(
                    titleIcon, contentDescription = "Icon", modifier = Modifier.size(24.dp)
                )

            })


    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Opções de Viagem",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            MapComponent(
                mapBitmap = null, modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            } else {
                LazyColumn {
                    rideEstimate?.options?.let {
                        items(it.size) { option ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = "Motorista: ${rideOptions[option].name}",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(text = "Descrição: ${rideOptions[option].description}")
                                    Text(text = "Veículo: ${rideOptions[option].vehicle}")
                                    Text(text = "Avaliação: ${rideOptions[option].review.rating}")
                                    Text(text = "Valor: R$ ${rideOptions[option].value}")

                                    Spacer(modifier = Modifier.height(8.dp))

                                    ElevatedButton(
                                        onClick = {}
                                    ) {
                                        Text( text = "Escolher")
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}