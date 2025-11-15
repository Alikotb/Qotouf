package com.muslim.qotouf.ui.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.qotouf.R
import com.muslim.qotouf.utils.extensions.convertNumbersToArabic

@Preview(showBackground = true)
@Composable
fun AyahMarkComponent(
    ayahNumber: Int = 199,
    size: Int = 36,
    fontSize: Int = 14,
    fontWeight: FontWeight = FontWeight.Bold,
) {
    Box(
        modifier = Modifier.size(size.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ayah_marke),
            contentDescription = "Ayah Mark",
            modifier = Modifier.fillMaxSize()
        )

        Text(
            text = ayahNumber.toString().convertNumbersToArabic(),
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            color = Color.Black,
            maxLines = 1
        )
    }
}
