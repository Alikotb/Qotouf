package com.muslim.anees.ui.screens.hisn_almuslim.components

import TitleCard
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muslim.qotouf.data.model.TafsierDtoItem
import com.muslim.qotouf.data.model.Verse
import com.muslim.qotouf.ui.screens.home.view.component.HisnAlMuslimTextCard

@Composable
fun TafsierScreenComponent(
    titlesWithTexts: List<Pair<Verse, TafsierDtoItem>>,
    expandedStates: MutableMap<String, Boolean>
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        titlesWithTexts.forEach { (title, tafsier) ->
            item {
                TitleCard(
                    title = title.text,
                    isExpanded = expandedStates[title.text] == true,
                    onToggleExpand = {
                        expandedStates[title.text] = !(expandedStates[title.text] ?: false)
                    }
                )
            }

            if (expandedStates[title.text] == true) {
                item {
                    HisnAlMuslimTextCard(textContent = tafsier.tafseir)
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}