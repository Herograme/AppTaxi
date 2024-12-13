package br.com.samuelborges.driveflow.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.samuelborges.driveflow.ui.screens.rideHistory.DriverItem

@Composable
fun DriverFilterSelector(
    drivers: List<DriverItem>, onSelectionChange: (DriverItem) -> Unit, modifier: Modifier = Modifier
) {
    val driverNull = DriverItem(null, "Todos Motoristas")
    var expanded by remember { mutableStateOf(false) }
    var selectedDriver by remember { mutableStateOf<DriverItem>(driverNull) }
    var tempSelected by remember { mutableStateOf<DriverItem>(driverNull) }



    Column(
        modifier = modifier
    ) {
        Box {
            OutlinedButton(
                onClick = { expanded = true },
                shape = MaterialTheme.shapes.small

            ) {
                Text(
                    text = "Selecionar Motorista",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()

            ) {
                DropdownMenuItem(
                    text = { Text(text = "Todos Motoristas") },
                    onClick = { tempSelected = driverNull },
                    trailingIcon = {
                        if (tempSelected.id == null) {
                            Icon(Icons.Default.Check, contentDescription = "Selecionado")
                        }
                    }
                )

                drivers.forEach { driver ->
                    DropdownMenuItem(
                        text = { Text(driver.name, fontSize = 16.sp) },
                        onClick = {
                            tempSelected = driver
                        },
                        trailingIcon = {
                            if (tempSelected.id == driver.id) {
                                Icon(Icons.Default.Check, contentDescription = "Selecionado")
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        onSelectionChange(tempSelected)
                        selectedDriver = tempSelected
                        expanded = false
                    },
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(text = "Aplicar Filtro")
                }
            }
        }
    }


}