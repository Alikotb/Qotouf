package com.muslim.qotouf.ui.common.helper

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun rememberScreenshotAnimation(): Pair<Float, () -> Unit> {
    val scale = remember { Animatable(1f) }
    var triggerAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(triggerAnimation) {
        if (triggerAnimation) {
            scale.animateTo(0.9f, animationSpec = tween(100))
            scale.animateTo(1f, animationSpec = tween(150))
            triggerAnimation = false
        }
    }

    val trigger: () -> Unit = { triggerAnimation = true }
    return scale.value to trigger
}