package br.com.samuelborges.driveflow.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LinedTopBar(
    title: String,
    navigationIcon: @Composable (() -> Unit)? = null,
    titleIcon: @Composable (() -> Unit)? = null, // Ícone ao lado do título
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    height: Dp = 56.dp,
    dividerColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
    dividerHeight: Dp = 1.dp
) {
    Column {
        Surface(
            color = backgroundColor,
            contentColor = contentColor,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height),
                contentAlignment = Alignment.Center
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        color = contentColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (titleIcon != null) {
                        Spacer(modifier = Modifier.width(8.dp)) // Espaço entre título e ícone
                        titleIcon()
                    }
                }


                if (navigationIcon != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 8.dp)
                            .size(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        navigationIcon()
                    }
                }


                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    content = actions
                )
            }
        }

        HorizontalDivider(
            thickness = dividerHeight,
            color = dividerColor
        )
    }
}