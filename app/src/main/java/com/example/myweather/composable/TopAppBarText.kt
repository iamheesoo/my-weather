package com.example.myweather.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.myweather.ui.theme.SubTitle2

@Composable
@Preview
fun TopAppBarText(
    modifier: Modifier = Modifier,
    text: String = "text",
    style: TextStyle = SubTitle2
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = style.fontSize,
        fontWeight = style.fontWeight,
        color = Color.White
    )
}