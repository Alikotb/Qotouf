package com.muslim.qotouf.ui.common.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SebhaImageBottomSheet(
    bottomSheetState: MutableState<Boolean>,
) {
    val sheetState = rememberModalBottomSheetState()
    val colors = MaterialTheme.colorScheme
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp

        ModalBottomSheet(
            onDismissRequest = { bottomSheetState.value = false },
            sheetState = sheetState,
            containerColor = Color(0xFFF5F5DB),
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = (screenHeight * .35).dp, max = (screenHeight * .55).dp)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .background(Color(0xFFF5F5DB)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "الإعدادت",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = colors.primary,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 12.dp)
                )


            }
        }

}

