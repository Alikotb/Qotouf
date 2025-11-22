package com.muslim.qotouf.ui.screens.home.view.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.qotouf.R

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun HomeCard(onclick: () -> Unit = {},cardTitle: String = "قطف جديد") {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val cardWidth = screenWidth * 0.45f
    val cardHeight = cardWidth * 0.65f
    Box(
        modifier = Modifier
            .height(cardHeight)
            .width(cardWidth)
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF3A7F62), // soft light
                        Color(0xFF2A6148), // soft base
                        Color(0xFF1B4632)  // soft dark
                    )
                )
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onclick()
            },
        contentAlignment = Alignment.Center
    ) {


        Image(
            painter = painterResource(id =R.drawable.tree),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier =  Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x68000000))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = cardTitle,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.hafs)),
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,

            )

        }

    }
}