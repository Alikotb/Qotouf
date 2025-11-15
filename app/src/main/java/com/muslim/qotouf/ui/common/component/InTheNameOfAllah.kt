package com.muslim.qotouf.ui.common.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.muslim.qotouf.ui.common.theme.BasmalahTextStyle


@Composable
fun InTheNameOfAllah(modifier: Modifier = Modifier) {
    Text(
        "بِســـــــــْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ",
        style = BasmalahTextStyle,
        modifier = modifier.fillMaxWidth()
    )
}