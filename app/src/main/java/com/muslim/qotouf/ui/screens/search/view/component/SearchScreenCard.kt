package com.muslim.qotouf.ui.screens.search.view.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.qotouf.data.model.Verse
import com.muslim.qotouf.enum.QuranSurah
import com.muslim.qotouf.ui.screens.home.view.component.CombinedAyatText

@Composable
fun SearchScreenCard(
    ayah: Verse,
    onAyahClick: () -> Unit = {},
    isDarkTheme: MutableState<Boolean>
) {
    val colors = MaterialTheme.colorScheme
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, colors.primary, RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onAyahClick()
            }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            CombinedAyatText(
                ayahList = listOf(ayah),
                isDarkTheme = isDarkTheme
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "سورة ${QuranSurah.getSurahName(ayah.chapter)} ",
                fontSize = 14.sp,
                color = colors.secondary,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}