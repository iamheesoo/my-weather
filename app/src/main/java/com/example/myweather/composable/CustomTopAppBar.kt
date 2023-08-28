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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweather.ui.theme.SubTitle1
import com.example.myweather.ui.theme.TopAppBarTitle

@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    height: Dp = 400.dp,
    locationName: String?,
    temp: Int?,
    description: String?,
    tempMin: Int?,
    tempMax: Int?
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
            text = locationName ?: "-", style = SubTitle1
        )
        TopAppBarText(
            text = "$temp°", style = TopAppBarTitle
        )
        TopAppBarText(
            text = description ?: "-",
        )
        Row {
            TopAppBarText(text = if (tempMax != null)"최고 $tempMax°" else "-")
            TopAppBarText(
                modifier = Modifier.padding(start = 10.dp),
                text =  if (tempMax != null)"최저 $tempMin°" else "-"
            )
        }
    }
}