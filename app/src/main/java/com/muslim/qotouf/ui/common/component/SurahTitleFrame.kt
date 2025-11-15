package com.muslim.qotouf.ui.common.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.qotouf.R

@SuppressLint("ConfigurationScreenWidthHeight")
@Preview(showBackground = true)
@Composable
fun SurahTitleFrame(
    surahTitle: String = "آلِ عِمْرَانَ",
    fontSize: Int = 20,
    fontWeight: FontWeight = FontWeight.Bold,
    ) {

    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.surah_framework),
            contentDescription = "Ayah Mark",
            modifier = Modifier.height(48.dp).width(screenWidth *0.9f),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = "سورة $surahTitle",
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            color = Color.Black,
            maxLines = 1,
            fontFamily = FontFamily(Font(R.font.thules)),
        )
    }

}