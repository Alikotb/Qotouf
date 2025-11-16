package com.muslim.qotouf.ui.screens.thumera.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.muslim.qotouf.data.model.Verse
import com.muslim.qotouf.enum.QuranSurah
import com.muslim.qotouf.ui.common.component.InTheNameOfAllah
import com.muslim.qotouf.ui.common.component.SurahTitleFrame
import com.muslim.qotouf.ui.common.helper.captureComposable
import com.muslim.qotouf.ui.common.helper.rememberScreenshotAnimation
import com.muslim.qotouf.ui.screens.home.view.component.CombinedAyatText
import com.muslim.qotouf.ui.screens.thumera.view_model.ScreenshotController


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun ThumeraScreen(
    modifier: Modifier = Modifier,
    ayah: Verse,
    innerPadding: PaddingValues,
    isDarkTheme: MutableState<Boolean>,
    screenshotController: ScreenshotController
) {
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp

    val (scale, triggerScreenshot) = rememberScreenshotAnimation()
    var flashVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale
            )
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .captureComposable(screenshotController) {
                            triggerScreenshot()
                            flashVisible = true
                        }
                ) {
                    Spacer(Modifier.height(screenHeight * 0.05f))
                    SurahTitleFrame(surahTitle = QuranSurah.getSurahName(ayah.chapter))
                    Spacer(Modifier.height(16.dp))
                    InTheNameOfAllah()
                    Spacer(Modifier.height(16.dp))
                    CombinedAyatText(
                        ayahList = listOf(ayah),
                        isDarkTheme = isDarkTheme
                    )
                    Spacer(Modifier.height(screenHeight * 0.05f))
                }
            }
        }

        if (flashVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.5f))
            )

            LaunchedEffect(flashVisible) {
                kotlinx.coroutines.delay(150)
                flashVisible = false
            }
        }
    }
}



