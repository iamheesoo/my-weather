package com.example.myweather.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Objects

@Parcelize
data class LatAndLon(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
): Parcelable {
    override fun equals(other: Any?): Boolean {
        return this.latitude == (other as? LatAndLon)?.latitude
                && this.longitude == (other as? LatAndLon)?.longitude
    }

    override fun hashCode(): Int {
        return Objects.hash(latitude, longitude)
    }
}