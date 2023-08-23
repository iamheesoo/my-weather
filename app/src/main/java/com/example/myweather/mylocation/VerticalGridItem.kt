package com.example.myweather.mylocation

import androidx.annotation.DrawableRes
import com.example.myweather.R

enum class VerticalGridItem(
    @DrawableRes
    val icon: Int,
    val title: String,
) {
    UV(R.drawable.round_wb_sunny_24, "자외선 지수"),
    SUNSET(R.drawable.round_wb_sunny_24, "일몰"),
    WIND(R.drawable.round_wb_sunny_24, "바람"),
    RAINY(R.drawable.round_water_drop_24, "강우"),
    TEMPERATURE(R.drawable.round_thermostat_24, "체감 온도"),
    HUMIDITY(R.drawable.round_water_24, "습도"),
    VISIBILITY(R.drawable.round_remove_red_eye_24, "가시거리"),
    PRESSURE(R.drawable.round_compress_24, "기압")
}