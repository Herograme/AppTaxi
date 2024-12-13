package br.com.samuelborges.driveflow.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.samuelborges.driveflow.R


@Composable
fun Welcome(paddingValues: Dp = 0.dp) {
    val image = painterResource(id = R.drawable.bannertransparent)
    val buttonColor = ButtonColors(
        containerColor = MaterialTheme.colorScheme.onSurface,
        contentColor = MaterialTheme.colorScheme.surface,
        disabledContainerColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledContentColor = MaterialTheme.colorScheme.surfaceDim
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(color = MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),

        ) {
        Column(
            modifier = Modifier
                .padding(16.dp, 16.dp)
                .fillMaxHeight(0.3f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Driver Flow",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "slogan ........",
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        //Spacer(modifier = Modifier.padding(spacer))
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp, 0.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .clip(RoundedCornerShape(16.dp))
        )
        //Spacer(modifier = Modifier.padding(spacer))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(16.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                onClick = { /*TODO*/ }, modifier = Modifier
                    .fillMaxWidth(.9f)
                    .height(60.dp),
                shape = MaterialTheme.shapes.large,
                colors = buttonColor
            ) {
                Text(text = "Join Now")

            }
            Spacer(modifier = Modifier.padding(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account?",
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Login",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
