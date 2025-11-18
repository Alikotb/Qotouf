package com.muslim.qotouf.ui.screens.home.view.scrrens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muslim.anees.ui.screens.hisn_almuslim.components.TafsierScreenComponent
import com.muslim.qotouf.data.model.Verse
import com.muslim.qotouf.ui.common.component.LoadingSection
import com.muslim.qotouf.ui.common.component.SurahTitleFrame
import com.muslim.qotouf.ui.screens.home.view_model.CurentDayTafsierViewModel


@Composable
fun CurentDayTafsirScreen(
    surah: String,
    innerPadding: PaddingValues,
    viewModel: CurentDayTafsierViewModel = hiltViewModel(),
    ayahList: List<Verse>,
) {
    val expandedStates = remember { mutableStateMapOf<String, Boolean>() }

    val isLoading by viewModel.loading.collectAsStateWithLifecycle()
    val tafierList by viewModel.curentDayTafsier.collectAsStateWithLifecycle()
    val colors = MaterialTheme.colorScheme

    LaunchedEffect(Unit) {
        viewModel.getCurentDayQatfTafseer(ayahList)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.surfaceContainer)
            .padding(innerPadding),
    ) {
        Spacer(Modifier.height(16.dp))
        SurahTitleFrame(surahTitle = surah)
        Spacer(Modifier.height(16.dp))

        if (!isLoading) {
            TafsierScreenComponent(
                titlesWithTexts = tafierList,
                expandedStates = expandedStates,
            )

        } else {
                LoadingSection()
                Spacer(Modifier.height(32.dp))


        }
    }
}