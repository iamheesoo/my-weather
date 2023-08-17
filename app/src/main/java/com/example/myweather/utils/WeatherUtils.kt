package com.example.myweather.utils

import android.content.Context
import com.example.myweather.R

fun Context.getWeatherIconDrawable(name: String?): Int {
    return kotlin.runCatching {
        resources.getIdentifier("icon_${name}", "drawable", packageName)
    }.getOrDefault(R.drawable.icon_03d)
}