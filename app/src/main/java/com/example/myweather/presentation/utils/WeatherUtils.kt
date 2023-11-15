package com.example.myweather.presentation.utils

import android.content.Context
import com.example.myweather.R

fun Context.getWeatherIconDrawable(name: String?): Int {
    return kotlin.runCatching {
        resources.getIdentifier("icon_${name}", "drawable", packageName)
    }.getOrDefault(R.drawable.icon_03d)
}

fun getAirQualityInfo(airQuality: Int?): String {
    return when (airQuality) {
        1 -> "Good"
        2 -> "Fair"
        3 -> "Moderate"
        4 -> "Poor"
        5 -> "Very poor"
        else -> "-"
    }
}