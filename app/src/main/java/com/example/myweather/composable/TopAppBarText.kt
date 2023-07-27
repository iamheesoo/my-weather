package com.example.myweather.composable

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun TopAppBarText(
    modifier: Modifier = Modifier,
    text: String = "text",
    fontSize: TextUnit = 15.sp
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize,
        color = Color.White
    )
}