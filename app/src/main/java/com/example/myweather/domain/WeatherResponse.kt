package com.example.myweather.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("coord")
    val coord: CoordData? = null,
    @SerialName("weather")
    val weather: List<WeatherData>? = null,
    @SerialName("base")
    val base: String? = null,
    @SerialName("main")
    val main: MainData? = null,
    @SerialName("visibility")
    val visibility: Int? = null,
    @SerialName("wind")
    val wind: WindData? = null,
    @SerialName("clouds")
    val clouds: CloudsData? = null,
    @SerialName("rain")
    val rain: VolumeData? = null,
    @SerialName("snow")
    val snow: VolumeData? = null,
    @SerialName("dt")
    val dt: Long? = null,
    @SerialName("sys")
    val sys: SysData? = null,
    @SerialName("timezone")
    val timezone: Long? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("cod")
    val cod: Int? = null
)

@Serializable
data class CoordData(
    @SerialName("lon")
    val lon: Double? = null,
    @SerialName("lat")
    val lat: Double? = null
)

@Serializable
data class WeatherData(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("main")
    val main: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("icon")
    val icon: String? = null
)

@Serializable
data class MainData(
    @SerialName("temp")
    val temp: Double? = null,
    @SerialName("feels_like")
    val feelsLike: Double? = null,
    @SerialName("temp_min")
    val tempMin: Double? = null,
    @SerialName("temp_max")
    val tempMax: Double? = null,
    @SerialName("pressure")
    val pressure: Int? = null,
    @SerialName("humidity")
    val humidity: Int? = null,
    @SerialName("sea_level")
    val seaLevel: Int? = null,
    @SerialName("grnd_level")
    val grndLevel: Int? = null
)

@Serializable
data class WindData(
    @SerialName("speed")
    val speed:Double? = null,
    @SerialName("deg")
    val deg: Int? = null,
    @SerialName("gust")
    val gust: Double? = null
)

@Serializable
data class CloudsData(
    @SerialName("all")
    val all: Int? = null
)

@Serializable
data class VolumeData(
    @SerialName("1h")
    val hour1: Double? = null,
    @SerialName("3h")
    val hour3: Double? = null
)

@Serializable
data class SysData(
    @SerialName("type")
    val type: Int? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("country")
    val country: String? = null,
    @SerialName("sunrise")
    val sunrise: Long? = null,
    @SerialName("sunset")
    val sunset: Long? = null
)