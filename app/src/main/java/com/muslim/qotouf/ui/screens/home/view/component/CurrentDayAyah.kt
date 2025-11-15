package com.muslim.qotouf.ui.screens.home.view.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.qotouf.ui.common.component.AyahMarkComponent
import com.muslim.qotouf.ui.common.theme.AyahTextStyle

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun CurrentDayAyah(
    ayahText: String,
    ayahNumber: Int= 199
) {

    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp
    val ayahMarkId = "ayahMark"

    val inlineContent = mapOf(
        ayahMarkId to InlineTextContent(
            Placeholder(
                width = 28.sp,
                height = 28.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            AyahMarkComponent(ayahNumber, size = 24, fontSize = 10)
        }
    )

    Text(
        text = buildAnnotatedString {
            append(ayahText)
            append(" ")
            appendInlineContent(ayahMarkId, ayahNumber.toString())
        },
        style = AyahTextStyle,
        inlineContent = inlineContent,
        modifier = Modifier.fillMaxWidth().padding(horizontal = (screenWidth * 0.05f))
    )
}


