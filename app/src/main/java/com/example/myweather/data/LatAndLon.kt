package com.example.myweather.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LatAndLon(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
): Parcelable