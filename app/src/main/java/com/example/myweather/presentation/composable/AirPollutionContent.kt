package com.example.myweather.presentation.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.myweather.presentation.theme.Azure
import com.example.myweather.presentation.theme.Content3
import com.example.myweather.presentation.theme.DeepRed
import com.example.myweather.presentation.theme.Orange
import com.example.myweather.presentation.theme.PrimaryTextColor

@Composable
fun AirPollutionContent(modifier: Modifier = Modifier, aqi: Int, content: String) {
    var canvasSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 14.dp)
    ) {
        Text(
            text = "대기질 Level $aqi (1~5)",
            color = PrimaryTextColor
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = content,
            color = PrimaryTextColor,
            fontSize = Content3.fontSize
        )
        Canvas(
            Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .height(10.dp)
                .onSizeChanged {
                    canvasSize = it
                }
        ) {
            drawRoundRect(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Azure,
                        Color.Green,
                        Color.Yellow,
                        Orange,
                        Color.Red,
                        DeepRed
                    )
                ),
                cornerRadius = CornerRadius(20f)
            )

            drawCircle(
                color = Color.White,
                center = Offset((canvasSize.width.toFloat() / 5 * aqi), 5.dp.toPx())
            )
        }
    }
}