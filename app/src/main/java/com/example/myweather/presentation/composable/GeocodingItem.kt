package com.example.myweather.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myweather.presentation.extensions.onClick
import com.example.myweather.presentation.theme.PrimaryTextColor
import com.example.myweather.presentation.theme.SubTitle2
import com.example.myweather.presentation.theme.SubTitle3

@Composable
fun GeocodingItem(koCity: String, enCity: String, lat: Double, lon: Double, onItemClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(vertical = 3.dp)
            .onClick { onItemClick.invoke() }
            .fillMaxWidth()
            .background(shape = RoundedCornerShape(16.dp), color = Color.DarkGray)
            .padding(10.dp)
    ) {
        Text(
            text = koCity,
            color = PrimaryTextColor,
            style = SubTitle2
        )
        Text(
            text = enCity,
            color = PrimaryTextColor,
            style = SubTitle3
        )
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = "$lat, $lon",
            color = PrimaryTextColor,
            style = SubTitle3
        )
    }
}