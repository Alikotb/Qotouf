package com.muslim.qotouf.ui.screens.home.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.qotouf.ui.common.theme.AppParBackgroundColor

@Preview(showBackground = true, locale = "ar")
@Composable
fun HomeAppBar(
    onModeClick: () -> Unit = {},
    onSettingClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .height(64.dp)
            .fillMaxWidth()
            .background(AppParBackgroundColor)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "قـطــــوف",
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            color = Color.White
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        onModeClick()
                    }
                    .padding(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LightMode,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    tint = Color(0xfffdfffe)
                )
            }
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        onSettingClick()
                    }
                    .padding(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    tint = Color(0xfffdfffe)
                )
            }
        }


    }


}