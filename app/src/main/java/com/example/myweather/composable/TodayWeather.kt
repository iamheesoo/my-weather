package com.example.myweather.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.myweather.ui.theme.Orange
import com.example.myweather.ui.theme.PrimaryTextColor

@Composable
fun TodayWeather(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    tempMin: Int?,
    tempMax: Int?
) {
    ConstraintLayout(
        modifier = modifier
            .padding(horizontal = 8.dp),
    ) {
        val (today, image, minText, rect, maxText) = createRefs()
        Text(
            modifier = Modifier.constrainAs(today) {
                linkTo(
                    start = parent.start,
                    end = image.start,
                    top = parent.top,
                    bottom = parent.bottom
                )
            },
            text = "오늘",
            color = PrimaryTextColor
        )
        Image(
            modifier = Modifier
                .padding(start = 10.dp)
                .constrainAs(image) {
                    linkTo(
                        start = today.end,
                        end = minText.start,
                        top = parent.top,
                        bottom = parent.bottom
                    )
                },
            painter = painterResource(id = icon),
            contentDescription = "icon_weather",
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier
                .padding(start = 10.dp)
                .constrainAs(minText) {
                    linkTo(
                        start = image.end,
                        end = rect.start,
                        top = parent.top,
                        bottom = parent.bottom
                    )
                },
            text = if (tempMin != null) "${tempMin}°" else "-",
            color = PrimaryTextColor
        )
        Canvas(
            modifier = Modifier
                .padding(start = 10.dp)
                .height(10.dp)
                .constrainAs(rect) {
                    linkTo(
                        start = minText.end,
                        end = maxText.start,
                        top = parent.top,
                        bottom = parent.bottom
                    )
                    width = Dimension.fillToConstraints
                }

        ) {
            drawRoundRect(
                brush = Brush.horizontalGradient(
                    colors = listOf(Orange, Color.Red)
                ),
                cornerRadius = CornerRadius(20f)
            )
        }
        Text(
            modifier = Modifier
                .padding(start = 10.dp)
                .constrainAs(maxText) {
                    linkTo(
                        start = rect.end,
                        end = parent.end,
                        top = parent.top,
                        bottom = parent.bottom
                    )
                },
            text = if (tempMax != null) "${tempMax}°" else "-",
            color = PrimaryTextColor
        )

        createHorizontalChain(today, image, minText, rect, maxText, chainStyle = ChainStyle.Spread)
    }
}
