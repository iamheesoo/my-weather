package com.example.myweather.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherHourlyResponse(
    @SerialName("cod")
    val cod: String? = null,
    @SerialName("message")
    val message: Int? = null,
    @SerialName("cnt")
    val cnt: Int? = null,
    @SerialName("list")
    val list: List<HourlyData>? = null,
    @SerialName("city")
    val city: CityData? = null
)

@Serializable
data class HourlyData(
    @SerialName("dt")
    val dt: Long? = null,
    @SerialName("main")
    val main: MainData? = null,
    @SerialName("weather")
    val weatherList: List<WeatherData>? = null,
    @SerialName("clouds")
    val clouds: CloudsData? = null,
    @SerialName("wind")
    val wind: WindData? = null,
    @SerialName("visibility")
    val visibility: Int? = null,
    @SerialName("pop")
    val pop: Double? = null,
    @SerialName("sys")
    val sys: SysData? = null,
    @SerialName("dt_txt")
    val dtTxt: String? = null
)

@Serializable
data class CityData(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String,
    @SerialName("coord")
    val coord: CoordData? = null,
    @SerialName("country")
    val country: String? = null,
    val sunrise: Long? = null,
    @SerialName("sunset")
    val sunset: Long? = null,
    @SerialName("timezone")
    val timezone: Long? = null,
    @SerialName("population")
    val population: Int? = null
)