package com.muslim.qotouf.ui.screens.setting.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSlider(
    sliderPosition: Int ,
    onPositionChange: (Int) -> Unit
) {
    var sliderPositionFloat by remember(sliderPosition) { mutableFloatStateOf(sliderPosition.toFloat()) }

    val colors = MaterialTheme.colorScheme

    Column {
        Slider(
            value = sliderPositionFloat,
            onValueChange = {
                val rounded = (it / 2).roundToInt() * 2
                sliderPositionFloat = rounded.toFloat()
                onPositionChange(rounded)
            },
            valueRange = 16f..36f,
            steps = (36 - 16) / 2 - 1,
            colors = SliderDefaults.colors(
                thumbColor = colors.primary,
                activeTrackColor = colors.onSecondaryContainer,
                inactiveTrackColor = colors.surfaceContainer
            ),
            thumb = {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(color = colors.primary, shape = CircleShape)
                )
            }
        )
    }

}