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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muslim.qotouf.MainViewModel
import com.muslim.qotouf.data.model.Verse
import com.muslim.qotouf.enum.QuranSurah
import com.muslim.qotouf.ui.common.component.quraan.InTheNameOfAllah
import com.muslim.qotouf.ui.common.component.quraan.SurahTitleFrame
import com.muslim.qotouf.ui.common.helper.captureComposable
import com.muslim.qotouf.ui.common.helper.rememberScreenshotAnimation
import com.muslim.qotouf.ui.screens.home.view.component.CombinedAyatText
import com.muslim.qotouf.ui.screens.thumera.view.component.ThumeraBottomBar
import com.muslim.qotouf.ui.screens.thumera.view_model.ScreenshotController
import com.muslim.qotouf.ui.screens.thumera.view_model.ThumeraViewModel
import kotlinx.coroutines.delay


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun ThumeraScreen(
    modifier: Modifier = Modifier,
    snackBarHost: SnackbarHostState,
    mainViewModel: MainViewModel,
    ayah: Verse,
    innerPadding: PaddingValues,
    viewModel: ThumeraViewModel = hiltViewModel(),
    isDarkTheme: Boolean,
    screenshotController: ScreenshotController,
    onDayTafsierClick: (String, String) -> Unit
) {
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp
    val colors = MaterialTheme.colorScheme
    val textSize by mainViewModel.quranTextSize.collectAsStateWithLifecycle()


    val (scale, triggerScreenshot) = rememberScreenshotAnimation()
    var flashVisible by remember { mutableStateOf(false) }

    val ayahList by viewModel.versesList.collectAsStateWithLifecycle()
    val isPreviousEnabled by viewModel.isPreviousEnabledState.collectAsStateWithLifecycle()
    val isNextEnabled by viewModel.isNextEnabledState.collectAsStateWithLifecycle()
    val message by viewModel.message.collectAsStateWithLifecycle()


    LaunchedEffect(message) {
        message?.let {
            snackBarHost.showSnackbar(it)
        }
    }
    LaunchedEffect(ayah) {
        viewModel.initAyah(ayah)
    }

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

                .background(colors.surfaceContainer)
                .padding(innerPadding)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .captureComposable(screenshotController) {
                            flashVisible = true
                            triggerScreenshot()
                        }

                ) {
                    Spacer(Modifier.height(screenHeight * 0.02f))
                    SurahTitleFrame(surahTitle = QuranSurah.getSurahName(ayah.chapter))
                    Spacer(Modifier.height(12.dp))
                    InTheNameOfAllah()
                    Spacer(Modifier.height(12.dp))
                    CombinedAyatText(
                        textSize = textSize,
                        ayahList = ayahList,
                        isDarkTheme = isDarkTheme
                    )
                    Spacer(Modifier.height(screenHeight * 0.05f))
                }
            }
        }
        ThumeraBottomBar(
            surah = QuranSurah.getSurahName(ayah.chapter),
            modifier = Modifier
                .padding(bottom = 32.dp)
                .align(Alignment.BottomCenter),
            onBackClick = {
                val currentAyah = ayahList.firstOrNull() ?: ayah
                viewModel.onPreviousAyah(currentAyah)
            },
            onNextClick = {
                val currentAyah = ayahList.lastOrNull() ?: ayah
                viewModel.onNextAyah(currentAyah)
            },
            isBackEnabled = isPreviousEnabled,
            ayahList = ayahList,
            onDayTafsierClick = onDayTafsierClick,
            isNextEnabled = isNextEnabled
        )
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



