package com.example.myweather.utils

import kotlin.math.floor

fun Double.floorUnder4() = floor((this) * 10000) / 10000