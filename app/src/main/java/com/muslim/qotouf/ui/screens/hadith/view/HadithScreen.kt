package com.muslim.qotouf.ui.screens.hadith.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muslim.qotouf.MainViewModel
import com.muslim.qotouf.ui.common.component.LoadingSection
import com.muslim.qotouf.ui.common.helper.captureComposable
import com.muslim.qotouf.ui.common.helper.rememberScreenshotAnimation
import com.muslim.qotouf.ui.screens.hadith.view.component.HadithCard
import com.muslim.qotouf.ui.screens.hadith.view_model.HadithViewModel
import com.muslim.qotouf.ui.screens.thumera.view_model.ScreenshotController
import kotlinx.coroutines.delay

@Composable
fun HadithScreen(
    mainViewModel: MainViewModel,
    innerPadding: PaddingValues,
    screenshotController: ScreenshotController,
    viweModel: HadithViewModel = hiltViewModel()
) {

    val colors = MaterialTheme.colorScheme
    val hadith by viweModel.curentHadith.collectAsStateWithLifecycle()
    val loading by viweModel.loading.collectAsStateWithLifecycle()
    val (scale, triggerScreenshot) = rememberScreenshotAnimation()
    var flashVisible by remember { mutableStateOf(false) }

    val textSize by mainViewModel.adkarTextSize.collectAsStateWithLifecycle()



    LaunchedEffect(Unit) {
        viweModel.getHadith()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.surfaceContainer)
            .padding(innerPadding)
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale
            ),
    ) {
        LazyColumn {

            if (!loading) {
                item {
                    Spacer(Modifier.height(32.dp))
                    Column(
                        Modifier.captureComposable(screenshotController) {
                            flashVisible = true
                            triggerScreenshot()
                        }) {
                        Spacer(Modifier.height(32.dp))
                        HadithCard(
                            textSize = textSize,
                            textContent = hadith?.text ?: ""
                        )
                        Spacer(Modifier.height(32.dp))

                    }
                }
                item {
                    Spacer(Modifier.height(56.dp))
                }
            } else {
                item {
                    LoadingSection()
                }
            }
        }
        FloatingActionButton(
            onClick = {
                viweModel.getRandomHadith()
            },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .offset(y = (-16).dp, x = 12.dp),
            containerColor = colors.primary
        ) {
            Icon(
                imageVector = Icons.Default.Repeat,
                contentDescription = null,
                tint = Color.White
            )
        }

        if (flashVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.5f))
            )

            LaunchedEffect(flashVisible) {
                delay(150)
                flashVisible = false
            }
        }
    }
}

