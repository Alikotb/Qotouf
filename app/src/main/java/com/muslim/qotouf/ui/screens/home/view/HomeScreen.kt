package com.muslim.qotouf.ui.screens.home.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.muslim.qotouf.ui.common.component.AyahMarkComponent
import com.muslim.qotouf.ui.common.component.SurahTitleFrame
import com.muslim.qotouf.ui.screens.home.view.component.HomeAppBar

@Composable
fun HomeScreen(innerPadding: PaddingValues) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) {
        HomeAppBar()
        LazyColumn {
            item {
                SurahTitleFrame()
            }
            items(50) {
                Row {
                    Box {
                        Text(
                            text = "الشاشة الرئيسية",
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp
                        )
                    }
                    AyahMarkComponent()
                }
            }
        }
    }


}