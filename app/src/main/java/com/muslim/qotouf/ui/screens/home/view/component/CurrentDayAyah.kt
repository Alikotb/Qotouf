package com.muslim.qotouf.ui.screens.home.view.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
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
import com.muslim.qotouf.data.model.Verse
import com.muslim.qotouf.ui.common.component.quraan.AyahMarkComponent
import com.muslim.qotouf.utils.extensions.normalizeArabic


@Composable
fun CombinedAyatText(ayahList: List<Verse>, isDarkTheme: Boolean, textSize: Int) {

    val colors = MaterialTheme.colorScheme
    val inlineContentMap = mutableMapOf<String, InlineTextContent>()

    val text = buildAnnotatedString {
        ayahList.forEach { verse ->
            val id = "ayah_${verse.verse}"
            val words = verse.text.split(" ")

            // append words (with color)
            for (word in words) {
                if (word.normalizeArabic().contains("الله") || word.normalizeArabic().contains("لله")) {
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append("$word ")
                    }
                } else {
                    withStyle(style = SpanStyle(color = colors.secondary)) {
                        append("$word ")
                    }
                }
            }
            appendInlineContent(id, verse.verse.toString())
            append(" ")

            inlineContentMap[id] = InlineTextContent(
                Placeholder(
                    width = 26.sp,
                    height = 26.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            ) {
                AyahMarkComponent(
                    isDarkTheme = isDarkTheme,
                    ayahNumber = verse.verse,
                    size = 22,
                    fontSize = 10
                )
            }
        }
    }

    val lineHeight = (textSize * 1.7f).sp

    Text(
        text = text,
        style = TextStyle(
            fontFamily = FontFamily(Font(R.font.hafs)),
            fontSize = textSize.sp,
            lineHeight = lineHeight,
            color = colors.secondary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        ),
        inlineContent = inlineContentMap,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}
