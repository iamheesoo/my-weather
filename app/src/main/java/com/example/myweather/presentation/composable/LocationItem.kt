package com.example.myweather.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myweather.R
import com.example.myweather.domain.local.LocationEntity
import com.example.myweather.presentation.theme.Content2
import com.example.myweather.presentation.theme.PrimaryTextColor
import com.example.myweather.presentation.theme.SubTitle1
import com.example.myweather.presentation.theme.TopAppBarTitle
import com.example.myweather.presentation.utils.compareTimes
import com.example.myweather.presentation.utils.convertUnixTimestampToUTC
import com.example.myweather.presentation.utils.getCurrentTime

@Composable
fun LocationItem(
    locationEntity: LocationEntity,
    isCurrentLocation: Boolean,
) {
    val currentTime = locationEntity.timezone.getCurrentTime()
    val sunset = convertUnixTimestampToUTC(locationEntity.sunset)
    val sunrise = convertUnixTimestampToUTC(locationEntity.sunrise)
    val background =
        if (compareTimes(currentTime, sunrise) > 0 && compareTimes(currentTime, sunset) < 0) {
            R.drawable.sunrise
        } else {
            R.drawable.sunset
        }
    Box(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Image(
            painter = painterResource(id = background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .clip(shape = RoundedCornerShape(16.dp))
        )
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
        ) {
            Text(
                text = if (isCurrentLocation) "나의 위치" else locationEntity.name,
                color = PrimaryTextColor,
                style = SubTitle1
            )
            Text(
                text = if (isCurrentLocation) locationEntity.name else locationEntity.timezone.getCurrentTime(),
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
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp),
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