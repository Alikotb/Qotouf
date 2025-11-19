package com.muslim.qotouf.ui.screens.home.view.scrrens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.muslim.qotouf.ui.common.component.LoadingSection
import com.muslim.qotouf.ui.common.component.ShowTafsierComponent
import com.muslim.qotouf.ui.common.component.quraan.InTheNameOfAllah
import com.muslim.qotouf.ui.common.component.quraan.SurahTitleFrame
import com.muslim.qotouf.ui.screens.home.view.component.CombinedAyatText
import com.muslim.qotouf.ui.screens.home.view.component.HomeCard
import com.muslim.qotouf.ui.screens.home.view_model.HomeViewModel

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel(),
    onSearchClick: () -> Unit,
    onHadithClick: () -> Unit,
    onDoaaClick: () -> Unit,
    onDayTafsierClick: (String, String) -> Unit,
    isDarkTheme: Boolean,
) {

    val ayahList by viewModel.curentDayAyah.collectAsState()
    val surahName by viewModel.curentSurah.collectAsState()
    val isLoading by viewModel.loading.collectAsState()

    val colors = MaterialTheme.colorScheme

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.surfaceContainer)
            .padding(innerPadding),
    ) {
        if (!isLoading) {
            item {
                Spacer(Modifier.height(16.dp))
                SurahTitleFrame(surahTitle = surahName)
            }
            item {
                Spacer(Modifier.height(16.dp))
                InTheNameOfAllah()
                Spacer(Modifier.height(12.dp))
            }
            item {
                Box {
                    CombinedAyatText(ayahList, isDarkTheme)
                }
            }
            item {
                ShowTafsierComponent(
                    ayahList = ayahList,
                    surahName = surahName,
                    onDayTafsierClick = onDayTafsierClick
                )
            }


        } else {
            item {
                LoadingSection()
                Spacer(Modifier.height(32.dp))

            }
        }
        item {
            Spacer(Modifier.height(32.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HomeCard(
                    onclick = {
                        viewModel.getCurentDayaQatf()
                    }
                )
                HomeCard(cardTitle = "إبحث عن ثمرة", onclick = onSearchClick)
            }
        }
        item {
            Spacer(Modifier.height(32.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HomeCard(
                    cardTitle = "ثمرة محمدية",
                    onclick = onHadithClick
                )
                HomeCard(cardTitle = "قنوت", onclick = onDoaaClick)
            }
            Spacer(Modifier.height(32.dp))


        }

    }
}


