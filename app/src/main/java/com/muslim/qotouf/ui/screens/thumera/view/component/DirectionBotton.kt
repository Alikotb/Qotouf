package com.muslim.qotouf.ui.screens.thumera.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true, locale = "ar", backgroundColor = 0xFF000000)
@Composable
fun DirectionBtn(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
    text:String="التالى",
    isRight: Boolean = true,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {

    Row(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                if(enabled){
                    onClick()
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isRight) {
            Icon(
                imageVector = icon,
                contentDescription = "Direction Button",
                tint =  if(enabled) Color.White else Color.White.copy(alpha=0.5f)
            )
            Text(
                text,
                style = MaterialTheme.typography.bodyLarge,
                color =  if(enabled) Color.White else Color.White.copy(alpha=0.5f),
                fontWeight = FontWeight.Bold
            )
        }else{
            Text(
                text,
                style = MaterialTheme.typography.bodyLarge,
                color = if(enabled) Color.White else Color.White.copy(alpha=0.5f),
                fontWeight = FontWeight.Bold

            )
            Icon(
                imageVector = icon,
                contentDescription = "Direction Button",
                tint =  if(enabled) Color.White else Color.White.copy(alpha=0.5f)
            )
        }

    }
}