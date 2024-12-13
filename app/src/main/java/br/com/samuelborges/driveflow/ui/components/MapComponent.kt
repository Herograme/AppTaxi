package br.com.samuelborges.driveflow.ui.components


import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp


@Composable
fun MapComponent(
    mapBitmap: Bitmap?,
    modifier: Modifier = Modifier,
    placeholderText: String = "Carregando mapa...",

) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
    ){
        if (mapBitmap != null) {
            Image(
                bitmap = mapBitmap.asImageBitmap(),
                contentDescription = "Mapa Estatico",
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Text(
                text = placeholderText,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
