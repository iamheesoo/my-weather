package com.example.myweather.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.myweather.R
import com.example.myweather.ui.theme.PrimaryTextColor

@Composable
@Preview
fun PressureContent(modifier: Modifier = Modifier, pressure: Int? = null) {
    var canvasSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    Box(
        modifier = modifier
            .width(100.dp)
            .height(100.dp),
        contentAlignment = Alignment.Center
//            .padding(10.dp)
    ) {
        Canvas(
            modifier = Modifier
                .matchParentSize()
                .onSizeChanged { canvasSize = it },
            onDraw = {
                drawArc(
                    color = Color.White,
                    startAngle = 140f,
                    sweepAngle = 260f,
                    useCenter = false,
                    topLeft = Offset(30f, 10f),
//                    topLeft = Offset(canvasSize.width - 20.dp.toPx() / 2, 10.dp.toPx()),
                    size = Size(200f, 200f),
                    style = Stroke(
                        width = 15f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    ),
                )
//                drawArc(
//                    color = Color.Red,
//                    startAngle = 200f,
//                    sweepAngle = 3f,
//                    useCenter = false,
//                    topLeft = Offset(50.dp.toPx(), 50.dp.toPx()),
//                    size = Size(200.dp.toPx(), 200.dp.toPx()),
//                    style = Stroke(width = 15f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f,10f),0f)),
//                )
            }
        )
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = if (pressure != null) "${pressure}\nhPa" else "--",
            color = PrimaryTextColor,
            textAlign = TextAlign.Center
        )
    }

}