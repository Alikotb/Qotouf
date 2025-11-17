package com.muslim.qotouf.ui.screens.thumera.view.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.muslim.qotouf.R

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun ThumeraBottomBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit ={},
    onNextClick: () -> Unit ={},
    isBackEnabled: Boolean,
    isNextEnabled: Boolean
) {
    val colors = MaterialTheme.colorScheme
    val config = LocalConfiguration.current
    val screenWith = config.screenWidthDp.dp
    val screenHeight = config.screenHeightDp.dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(screenHeight * 0.09f)
            .padding(horizontal = (screenWith * 0.2f)).padding(bottom = 24.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(colors.primary),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround

    ){
        DirectionBtn(
            icon = Icons.AutoMirrored.Filled.ArrowBackIos,
            text = "التالى",
            isRight = true,
            enabled = isNextEnabled,
            onClick = onNextClick
        )
        Icon(
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(36.dp)
        )
        DirectionBtn(
            icon = Icons.AutoMirrored.Filled.ArrowForwardIos,
            text = "السابق",
            isRight = false,
            enabled = isBackEnabled,
            onClick = onBackClick
        )

    }

}