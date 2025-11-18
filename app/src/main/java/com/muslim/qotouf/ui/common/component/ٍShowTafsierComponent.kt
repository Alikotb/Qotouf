package com.muslim.qotouf.ui.common.component

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.muslim.qotouf.data.model.Verse

@Composable
fun ShowTafsierComponent(
    ayahList: List<Verse>,
    surahName: String,
    onDayTafsierClick: (String, String)-> Unit
    ) {
    val colors = MaterialTheme.colorScheme
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp)
    ) {
        Text(
            text = "عرض التفسير",
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable(onClick = {
                    val json = Gson().toJson(ayahList)
                    val encodedJson = Uri.encode(json)
                    onDayTafsierClick(surahName,encodedJson)
                }),
            style = MaterialTheme.typography.bodyMedium.copy(
                textDecoration = TextDecoration.Underline,
                color = colors.primary
            )
        )
    }
}