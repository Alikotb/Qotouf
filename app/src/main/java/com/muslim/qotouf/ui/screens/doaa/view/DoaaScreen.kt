package com.muslim.qotouf.ui.screens.doaa.view

import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muslim.qotouf.R
import com.muslim.qotouf.ui.common.component.LoadingSection
import com.muslim.qotouf.ui.common.helper.captureComposable
import com.muslim.qotouf.ui.common.helper.rememberScreenshotAnimation
import com.muslim.qotouf.ui.screens.doaa.view_model.DoaaViewModel
import com.muslim.qotouf.ui.screens.hadith.view.component.HadithCard
import com.muslim.qotouf.ui.screens.thumera.view_model.ScreenshotController
import kotlinx.coroutines.delay

@Composable
fun DoaaScreen(
    screenshotController: ScreenshotController,
    innerPadding: PaddingValues,
    viweModel: DoaaViewModel = hiltViewModel()
) {

    val colors = MaterialTheme.colorScheme
    val doaa by viweModel.curentDoaa.collectAsStateWithLifecycle()
    val loading by viweModel.loading.collectAsStateWithLifecycle()
    val (scale, triggerScreenshot) = rememberScreenshotAnimation()
    var flashVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viweModel.getDoaa()
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
                .height(50.dp)
                .align(Alignment.TopCenter)
                .clip(RoundedCornerShape(8.dp))
                .background(colors.onSecondary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                doaa?.category ?: "",
                textAlign = TextAlign.Center,
                color =colors.secondary,
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.othmani)),
                    fontSize = 24.sp,
                    lineHeight = 45.sp,
                    color = colors.secondary,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                ),
            )
        }
        LazyColumn {
            if (!loading) {
                item {
                    Spacer(Modifier.height(70.dp))
                    Column(
                        Modifier.captureComposable(screenshotController) {
                            flashVisible = true
                            triggerScreenshot()
                        }) {
                    Spacer(Modifier.height(32.dp))
                        doaa?.duaa?.forEach {
                            HadithCard(
                                textContent = it
                            )
                            Spacer(Modifier.height(16.dp))
                        }
                        Spacer(Modifier.height(32.dp))
                    }
                }
            } else {
                item {
                    LoadingSection()
                }
            }
        }
        FloatingActionButton(
            onClick = {
                viweModel.getRandomDoaa()
            },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .offset(y = (-36).dp, x = 16.dp),
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

