package com.muslim.qotouf.ui.common.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.muslim.qotouf.R

val AyahTextStyle = TextStyle(
    fontFamily = FontFamily(Font(R.font.hafs)),
    fontSize = 22.sp,
    lineHeight = 36.sp,
    color = Color(0xFF161107),
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.SemiBold
)

val BasmalahTextStyle = TextStyle(
    fontFamily = FontFamily(Font(R.font.thules)),
    fontSize = 26.sp,
    lineHeight = 40.sp,
    color = Color.Black,
    textAlign = TextAlign.Center,
)