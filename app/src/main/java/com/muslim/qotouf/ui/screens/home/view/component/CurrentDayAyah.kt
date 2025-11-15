package com.muslim.qotouf.ui.screens.home.view.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.qotouf.R
import com.muslim.qotouf.data.model.Verse
import com.muslim.qotouf.ui.common.component.AyahMarkComponent


@Composable
fun CombinedAyatText(ayahList: List<Verse>) {

    val colors = MaterialTheme.colorScheme
    val inlineContentMap = mutableMapOf<String, InlineTextContent>()

    val text = buildAnnotatedString {
        ayahList.forEach { verse ->
            val id = "ayah_${verse.verse}"

            append(verse.text)
            append(" ")

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
                    ayahNumber = verse.verse,
                    size = 22,
                    fontSize = 10
                )
            }
        }
    }

    Text(
        text = text,
        style = TextStyle(
            fontFamily = FontFamily(Font(R.font.hafs)),
            fontSize = 22.sp,
            lineHeight = 36.sp,
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
