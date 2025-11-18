package com.muslim.qotouf.ui.screens.hadith.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muslim.qotouf.ui.common.component.LoadingSection
import com.muslim.qotouf.ui.screens.hadith.view.component.HadithCard
import com.muslim.qotouf.ui.screens.hadith.view_model.HadithViewModel

@Composable
fun HadithScreen(
    innerPadding: PaddingValues,
    viweModel: HadithViewModel = hiltViewModel()
) {

    val colors = MaterialTheme.colorScheme
    val hadith by viweModel.curentHadith.collectAsStateWithLifecycle()
    val loading by viweModel.loading.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viweModel.getHadith()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.surfaceContainer)
            .padding(innerPadding),
    ) {
        LazyColumn {

            if(!loading){
            item {
                Spacer(Modifier.height(64.dp))
                HadithCard(
                    textContent = hadith?.text?:""
                )
            }
            }else{
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
                .offset(y = (-80).dp, x = 16.dp),
            containerColor = colors.primary
        ) {
            Icon(
                imageVector = Icons.Default.Repeat,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

