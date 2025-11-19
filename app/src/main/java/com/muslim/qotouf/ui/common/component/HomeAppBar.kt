package com.muslim.qotouf.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.qotouf.ui.common.theme.AppParBackgroundColor

@Composable
fun HomeAppBar(
    appBarTitle: MutableState<String>,
    firstIcon: ImageVector,
    secondIcon: ImageVector,
    onSecondIconClick: () -> Unit ,
    ThirdComposableState: MutableState<@Composable (() -> Unit)?>? = null,
    onFirstIconClick: MutableState<() -> Unit>
) {
    Row(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .height(64.dp)
            .fillMaxWidth()
            .background(AppParBackgroundColor)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = appBarTitle.value,
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
                        onSecondIconClick()
                    }
                    .padding(0.dp)
            ) {
                Icon(
                    imageVector = secondIcon,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    tint = Color(0xfffdfffe)
                )
            }
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        onFirstIconClick.value()
                    }
                    .padding(0.dp)
            ) {
                Icon(
                    imageVector = firstIcon,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    tint = Color(0xfffdfffe)
                )
            }
            ThirdComposableState?.value?.let { it() }
        }


    }


}