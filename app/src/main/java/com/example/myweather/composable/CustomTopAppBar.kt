package com.example.myweather.composable

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    height: Dp = 400.dp,
    locationName: String = "서울특별시",
    temperature: Int = 33
) {
    Column(
        modifier = modifier
            .animateContentSize()
            .fillMaxWidth()
            .height(height),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBarText(
            text = locationName, fontSize = 28.sp
        )
        TopAppBarText(
            text = "$temperature°", fontSize = 48.sp
        )
        TopAppBarText(
            text = "한때 흐림",
        )
        Row {
            TopAppBarText(text = "최고 34°")
            TopAppBarText(
                modifier = Modifier.padding(start = 10.dp),
                text = "최저 23°"
            )
        }
    }
}