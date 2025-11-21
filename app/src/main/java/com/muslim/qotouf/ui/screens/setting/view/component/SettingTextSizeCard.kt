package com.muslim.qotouf.ui.screens.setting.view.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.qotouf.R

@Composable
fun SettingTextSizeCard(
    title: String = "حجم النص القرآني",
    example: String = "الرَّحْمَٰنُ عَلَى الْعَرْشِ اسْتَوَىٰ",
    textSize: Int ,
    onPositionChange: (Int) -> Unit = {}
    ) {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 12.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(1.dp, colors.primary, RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = colors.onSurfaceVariant
            )
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        title,
                        color = colors.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font(R.font.othmani))
                    )
                    Text(
                        "${textSize}",
                        color = colors.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp
                    )
                }
                CustomSlider(
                    sliderPosition = textSize,
                    onPositionChange = {
                        onPositionChange(it)
                    }
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(1.dp, Color(0xFFE3F7F4), RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = colors.surfaceContainer
            )
        ) {
            Text(
                example ,
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.hafs)),
                    fontSize = textSize.sp,
                    lineHeight = 36.sp,
                    color = colors.secondary,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.padding(16.dp,)
           )
        }
    }


}