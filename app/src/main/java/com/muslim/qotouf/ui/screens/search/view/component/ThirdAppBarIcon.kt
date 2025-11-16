package com.muslim.qotouf.ui.screens.search.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCameraBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchThirdAppBarIcon() {
    Box(
        modifier = Modifier
            .size(30.dp)
            .clickable {
            }
    ) {
        Icon(
            imageVector = Icons.Default.PhotoCameraBack,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            tint = Color(0xfffdfffe)
        )
    }
}