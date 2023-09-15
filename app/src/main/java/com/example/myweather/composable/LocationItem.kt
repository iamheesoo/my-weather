package com.example.myweather.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myweather.database.LocationEntity
import com.example.myweather.ui.theme.Content2
import com.example.myweather.ui.theme.PrimaryTextColor
import com.example.myweather.ui.theme.SubTitle1
import com.example.myweather.ui.theme.TopAppBarTitle

@Composable
fun LocationItem(
    locationEntity: LocationEntity,
    isCurrentLocation: Boolean,
) {
    Box(
        modifier = Modifier
            .padding(vertical = 3.dp)
            .fillMaxWidth()
            .background(shape = RoundedCornerShape(20.dp), color = Color.DarkGray)
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Text(
                text = if (isCurrentLocation) "나의 위치" else locationEntity.name,
                color = PrimaryTextColor,
                style = SubTitle1
            )
            Text(
                text = if (isCurrentLocation) locationEntity.name else "time",
                color = PrimaryTextColor,
                style = Content2
            )
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = locationEntity.weatherInfo,
                color = PrimaryTextColor,
                style = Content2
            )
        }
        Column(
            modifier = Modifier.align(Alignment.TopEnd),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "${locationEntity.temp}°",
                color = PrimaryTextColor,
                style = TopAppBarTitle
            )
            Row(
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Text(
                    text = "최고:${locationEntity.tempMax}°",
                    color = PrimaryTextColor,
                    style = Content2
                )
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = "최저:${locationEntity.tempMin}°",
                    color = PrimaryTextColor,
                    style = Content2
                )
            }
        }

    }
}