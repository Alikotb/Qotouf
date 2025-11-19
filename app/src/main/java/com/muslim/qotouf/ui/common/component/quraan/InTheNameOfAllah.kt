package com.muslim.qotouf.ui.common.component.quraan

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.muslim.qotouf.R


@Composable
fun InTheNameOfAllah(modifier: Modifier = Modifier) {
    val colors = MaterialTheme.colorScheme
    Text(
        "بِســـــــــْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ",
        style = TextStyle(
            fontFamily = FontFamily(Font(R.font.thules)),
            fontSize = 26.sp,
            lineHeight = 40.sp,
            color = colors.secondary,
            textAlign = TextAlign.Center,
        ),
        modifier = modifier.fillMaxWidth()
    )
}