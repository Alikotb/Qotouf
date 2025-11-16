package com.muslim.qotouf.ui.screens.thumera.view

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.muslim.qotouf.data.model.Verse

@Composable
fun ThumeraScreen(modifier: Modifier = Modifier, ayah: Verse, innerPadding: PaddingValues) {

    Log.d("ali", "ThumeraScreen: ${ayah.text}")
}