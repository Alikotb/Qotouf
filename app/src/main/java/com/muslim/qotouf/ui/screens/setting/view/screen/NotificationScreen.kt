package com.muslim.qotouf.ui.screens.setting.view.screen

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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.muslim.qotouf.MainViewModel
import com.muslim.qotouf.R
import com.muslim.qotouf.ui.common.component.quraan.SurahTitleFrame
import com.muslim.qotouf.ui.common.helper.captureComposable
import com.muslim.qotouf.ui.common.helper.rememberScreenshotAnimation
import com.muslim.qotouf.ui.screens.doaa.view_model.DoaaViewModel
import com.muslim.qotouf.ui.screens.hadith.view.component.HadithCard
import com.muslim.qotouf.ui.screens.hadith.view_model.HadithViewModel
import com.muslim.qotouf.ui.screens.home.view_model.HomeViewModel
import com.muslim.qotouf.ui.screens.thumera.view_model.ScreenshotController
import com.muslim.qotouf.utils.constant.AppConstant
import kotlinx.coroutines.delay

@Composable
fun NotificationScreen(
    message: String,
    title: String,
    type: String,
    innerPadding: PaddingValues,
    screenshotController: ScreenshotController,
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel = hiltViewModel(),
    hadithViewModel: HadithViewModel = hiltViewModel(),
    doaaViewModel: DoaaViewModel = hiltViewModel()
) {

    val colors = MaterialTheme.colorScheme
    val (scale, triggerScreenshot) = rememberScreenshotAnimation()
    var flashVisible by remember { mutableStateOf(false) }

    val quranTextSize by mainViewModel.quranTextSize.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        homeViewModel.getCurentDayaQatf()
        hadithViewModel.getRandomHadith()
        doaaViewModel.getRandomDoaa()
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
        Column {
            when (type) {
                AppConstant.QURAN_CHANEL_ID -> {
                    Spacer(Modifier.height(24.dp))
                    SurahTitleFrame(surahTitle = title)
                }

                else -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .padding(horizontal = 16.dp)
                            .height(50.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(colors.onSecondary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            if (type == AppConstant.DOAA_CHANEL_ID) title else "من صحيــح البخــاري",
                            textAlign = TextAlign.Center,
                            color = colors.secondary,
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
                    Spacer(Modifier.height(16.dp))
                }

            }

            LazyColumn {

                item {
                    Column(
                        Modifier.captureComposable(screenshotController) {
                            flashVisible = true
                            triggerScreenshot()
                        }) {
                        Spacer(Modifier.height(32.dp))
                        HadithCard(
                            textSize = quranTextSize,
                            textContent = message
                        )
                        Spacer(Modifier.height(32.dp))
                    }
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
                delay(150)
                flashVisible = false
            }
        }
    }


}