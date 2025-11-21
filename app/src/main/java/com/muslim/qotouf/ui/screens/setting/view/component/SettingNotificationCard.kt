package com.muslim.qotouf.ui.screens.setting.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.qotouf.R

@Composable
fun SettingNotificationCard(
    modifier: Modifier = Modifier,
    isEnabled: Boolean ,
    title: String = "إشعارات القراءن",
    onClick: () -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .height(64.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, colors.onSecondaryContainer, RoundedCornerShape(12.dp))
            .background(colors.onSurfaceVariant)
            .clickable {
                onClick()
            },
    ) {

        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.othmani)),
                color = colors.secondary
            )
            Box(
                modifier = Modifier.size(36.dp)
                    .clip(CircleShape)
                    .border(2.dp,Color(0xFF009788),CircleShape)
                    .background(
                        if (isEnabled) Color(0xFF009788)
                        else Color.Transparent
                    )
            ){}
        }
    }


}