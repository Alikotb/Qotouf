package com.muslim.qotouf.ui.common.component


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MySearchBar(
    modifier: Modifier,
    onValueChange: (String) -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }
    val colors = MaterialTheme.colorScheme

    TextField(
        modifier = modifier
            .height(56.dp)
            .border(
                width = 2.dp,
                color = colors.primary,
                shape = RoundedCornerShape(16.dp)
            ),
        value = searchText,
        onValueChange = {
            onValueChange(it)
            searchText = it
        },
        shape = MaterialTheme.shapes.medium,
        placeholder = { Text("إبحث عن ثمرة ...", fontSize = 14.sp, color = colors.secondary) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "",
                tint = colors.secondary
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colors.surfaceContainer,
            unfocusedContainerColor = colors.surfaceContainer,
            cursorColor = colors.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = colors.secondary,
            unfocusedTextColor = colors.secondary
        )

    )
}
