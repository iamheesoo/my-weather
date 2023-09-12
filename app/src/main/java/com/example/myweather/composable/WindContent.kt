package com.example.myweather.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.myweather.R
import com.example.myweather.ui.theme.Content4
import com.example.myweather.ui.theme.PrimaryTextColor

@Composable
fun WindContent(modifier: Modifier = Modifier, speed: Double, deg: Int?) {
    var canvasSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    val circleStroke = 5f
    Box(
        modifier = modifier
            .height(100.dp)
            .padding(10.dp)
    ) {
        Canvas(
            modifier = Modifier
                .matchParentSize()
                .onSizeChanged { canvasSize = it },
            onDraw = {
                drawCircle(
                    color = Color.White,
                    style = Stroke(width = circleStroke),
                    center = Offset(canvasSize.width.toFloat() / 2, canvasSize.height.toFloat() / 2)
                )
            }
        )

        Text(
            modifier = Modifier.align(Alignment.TopCenter),
            text = "북",
            color = PrimaryTextColor,
            fontSize = Content4.fontSize
        )
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            text = "서",
            color = PrimaryTextColor,
            fontSize = Content4.fontSize
        )
        Text(
            modifier = Modifier.align(Alignment.CenterEnd),
            text = "동",
            color = PrimaryTextColor,
            fontSize = Content4.fontSize
        )
        Text(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = "남",
            color = PrimaryTextColor,
            fontSize = Content4.fontSize
        )
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .zIndex(1f),
            text = "${speed}\nm/s",
            color = PrimaryTextColor,
            textAlign = TextAlign.Center
        )

        if (deg != null) {
            Image(
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.Center)
                    .rotate(deg - 90f),
                painter = painterResource(id = R.drawable.round_arrow_right_alt_24),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = Color.Black)
            )
        }

    }
}