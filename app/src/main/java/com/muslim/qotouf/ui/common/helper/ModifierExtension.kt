package com.muslim.qotouf.ui.common.helper

import android.graphics.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalView
import androidx.core.graphics.createBitmap
import com.muslim.qotouf.ui.screens.thumera.view_model.ScreenshotController


@Composable
fun Modifier.captureComposable(
    controller: ScreenshotController,
    onCaptureEffect: () -> Unit
): Modifier {
    val view = LocalView.current
    return this.then(
        Modifier.onGloballyPositioned { coordinates ->
            controller.requestCapture = {
                val width = coordinates.size.width
                val height = coordinates.size.height

                val bitmap = createBitmap(width, height)
                val canvas = Canvas(bitmap)

                val positionInWindow = coordinates.localToWindow(Offset.Zero)
                canvas.translate(-positionInWindow.x, -positionInWindow.y)

                view.draw(canvas)

                controller.onBitmapReady?.invoke(bitmap)
                controller.onBitmapReady = null

                onCaptureEffect()
            }
        }
    )
}
