package com.example.myweather.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweather.ui.theme.PrimaryTextColor
import com.example.myweather.ui.theme.SubTitle2

@Composable
fun HourWeather(hour: String?, temp: Int, @DrawableRes icon: Int) {
    Column(
        modifier = Modifier.padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (hour != null) "${hour}시" else "-",
            color = PrimaryTextColor,
            fontSize = SubTitle2.fontSize
        )
        Image(
            painter = painterResource(id = icon),
            contentDescription = "icon_weather",
            modifier = Modifier
                .size(30.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "$temp°",
            color = PrimaryTextColor,
            fontSize = SubTitle2.fontSize
        )
    }
}