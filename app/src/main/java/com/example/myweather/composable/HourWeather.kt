package com.example.myweather.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweather.R
import com.example.myweather.ui.theme.PrimaryTextColor

@Composable
fun HourWeather(hour: String?, temp: Int = 33) {
    Column(
        modifier = Modifier.padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (hour != null)"${hour}시" else "-",
            color = PrimaryTextColor,
            fontSize = 14.sp
        )
        Icon(
            painter = painterResource(id = R.drawable.baseline_cloud_24),
            contentDescription = "icon_weather",
            tint = PrimaryTextColor,
            modifier = Modifier.padding(vertical = 5.dp).size(20.dp)
        )
        Text(
            text = "$temp°",
            color = PrimaryTextColor,
            fontSize = 14.sp
        )
    }
}