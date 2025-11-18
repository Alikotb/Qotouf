package com.muslim.qotouf.ui.screens.doaa.view

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
import com.muslim.qotouf.ui.screens.doaa.view_model.DoaaViewModel
import com.muslim.qotouf.ui.screens.hadith.view.component.HadithCard

@Composable
fun DoaaScreen(
    innerPadding: PaddingValues,
    viweModel: DoaaViewModel = hiltViewModel()
) {

    val colors = MaterialTheme.colorScheme
    val doaa by viweModel.curentDoaa.collectAsStateWithLifecycle()
    val loading by viweModel.loading.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viweModel.getDoaa()
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
               doaa?.duaa?.forEach {
                   HadithCard(
                       textContent = it
                   )
                   Spacer(Modifier.height(24.dp))
               }
            }
            }else{
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

