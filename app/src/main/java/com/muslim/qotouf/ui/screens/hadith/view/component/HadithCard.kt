package com.muslim.qotouf.ui.screens.hadith.view.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.qotouf.R
import com.muslim.qotouf.utils.extensions.normalizeArabic

@Composable
fun HadithCard(textContent: String, textSize: Int) {

    val colors = MaterialTheme.colorScheme

    val highlightedText = buildAnnotatedString {
        val words = textContent.split(" ")
        for (word in words) {
            if (word.normalizeArabic().contains("الله")) {
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("$word ")
                }
            } else {
                withStyle(style = SpanStyle(color = colors.secondary)) {
                    append("$word ")
                }
            }
        }
    }
    val lineHeight = (textSize * 1.5f).sp

    Text(
        text = highlightedText,
        style = TextStyle(
            fontFamily = FontFamily(Font(R.font.hafs)),
            fontSize = textSize.sp,
            lineHeight = lineHeight,
            color = colors.secondary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}