package com.muslim.qotouf.ui.screens.home.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.muslim.qotouf.ui.common.component.InTheNameOfAllah
import com.muslim.qotouf.ui.common.component.SurahTitleFrame
import com.muslim.qotouf.ui.screens.home.view.component.CurrentDayAyah
import com.muslim.qotouf.ui.screens.home.view.component.HomeAppBar
import com.muslim.qotouf.ui.screens.home.view_model.HomeViewModel

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel()
    ) {

    val ayahList by viewModel.curentDayAyah.collectAsState()
    val surahName by viewModel.curentSurah.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) {

        HomeAppBar()
        LazyColumn {
            item {
                Spacer(Modifier.height(16.dp))
                SurahTitleFrame(surahTitle = surahName)
            }
            item {
                Spacer(Modifier.height(16.dp))
                InTheNameOfAllah()
                Spacer(Modifier.height(12.dp))
            }
            items(ayahList){
                CurrentDayAyah(ayahText = it.text, ayahNumber = it.verse)
            }
            item{
                Spacer(Modifier.height(16.dp))

            }
        }
    }


}