package com.muslim.qotouf.ui.screens.search.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muslim.qotouf.ui.common.component.LoadingSection
import com.muslim.qotouf.ui.common.component.MySearchBar
import com.muslim.qotouf.ui.screens.search.view.component.SearchScreenCard
import com.muslim.qotouf.ui.screens.search.view_model.SearchViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    viewModel: SearchViewModel = hiltViewModel(),
    isDarkTheme: MutableState<Boolean>,
    onAyahClick: (String, Int, Int) -> Unit,
) {

    val list by viewModel.versesList.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    val colors = MaterialTheme.colorScheme

    Column(
        modifier = modifier.fillMaxSize().background(colors.surfaceContainer).padding(innerPadding)
    ) {
        Spacer(Modifier.height(16.dp))
        MySearchBar(modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp)){
            viewModel.searchInQotouf(it)
        }

        if (isLoading) {
            LoadingSection()
        } else {
            Spacer(Modifier.height(16.dp))
            LazyColumn {
                items(
                    items = list,
                    key = { it.text }
                ) { ayah ->
                    SearchScreenCard(ayah = ayah,isDarkTheme = isDarkTheme ){a,b,c->
                        onAyahClick(a, b, c)
                    }
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}