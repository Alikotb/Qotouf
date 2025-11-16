package com.muslim.qotouf.ui.screens.thumera.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.muslim.qotouf.data.model.Verse
import com.muslim.qotouf.enum.QuranSurah
import com.muslim.qotouf.ui.common.component.InTheNameOfAllah
import com.muslim.qotouf.ui.common.component.SurahTitleFrame
import com.muslim.qotouf.ui.screens.home.view.component.CombinedAyatText

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun ThumeraScreen(
    modifier: Modifier = Modifier,
    ayah: Verse,
    innerPadding: PaddingValues,
    isDarkTheme: MutableState<Boolean>
) {
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp
    val screenWidth = config.screenWidthDp.dp
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(innerPadding)
    ) {
        item{
            Spacer(Modifier.height(screenHeight * 0.15f))
        }
        item {
            SurahTitleFrame(surahTitle = QuranSurah.getSurahName(ayah.chapter))
            Spacer(Modifier.height(16.dp))
            InTheNameOfAllah()
            Spacer(Modifier.height(16.dp))
            CombinedAyatText(ayahList = listOf(ayah), isDarkTheme = isDarkTheme)
        }

    }
}