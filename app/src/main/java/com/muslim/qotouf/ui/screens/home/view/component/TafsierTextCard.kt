package com.muslim.qotouf.ui.screens.home.view.component

import androidx.compose.foundation.border
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.qotouf.R


@Composable
fun HisnAlMuslimTextCard(textContent: String) {
    val colors = MaterialTheme.colorScheme

    val highlightedText = buildAnnotatedString {
        val words = textContent.split(" ")
        for (word in words) {
            if (word.contains("الله")) {
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

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .border(1.dp, Color(0xFFE3F7F4), RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = colors.surfaceContainer
            )
        ) {
            Text(
                text = highlightedText,
                fontSize = 18.sp,
                color = colors.secondary,
                fontFamily = FontFamily(Font(R.font.othmani)),
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(16.dp)
            )
        }

}