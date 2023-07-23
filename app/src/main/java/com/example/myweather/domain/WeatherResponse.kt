package com.example.myweather.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("coord")
    val coord: CoordData,
    @SerialName("weather")
    val weather: List<WeatherData>,
    @SerialName("base")
    val base: String,
    @SerialName("main")
    val main: MainData,
    @SerialName("visibility")
    val visibility: Int,
    @SerialName("wind")
    val wind: WindData,
    @SerialName("clouds")
    val clouds: CloudsData,
    @SerialName("rain")
    val rain: VolumeData? = null,
    @SerialName("snow")
    val snow: VolumeData? = null,
    @SerialName("dt")
    val dt: Long,
    @SerialName("sys")
    val sys: SysData,
    @SerialName("timezone")
    val timezone: Long,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("cod")
    val cod: Int
)

@Serializable
data class CoordData(
    @SerialName("lon")
    val lon: Double,
    @SerialName("lat")
    val lat: Double
)

@Serializable
data class WeatherData(
    @SerialName("id")
    val id: Int,
    @SerialName("main")
    val main: String,
    @SerialName("description")
    val description: String,
    @SerialName("icon")
    val icon: String
)

@Serializable
data class MainData(
    @SerialName("temp")
    val temp: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    @SerialName("temp_min")
    val tempMin: Double,
    @SerialName("temp_max")
    val tempMax: Double,
    @SerialName("pressure")
    val pressure: Int,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("sea_level")
    val seaLevel: Int,
    @SerialName("grnd_level")
    val grndLevel: Int
)

@Serializable
data class WindData(
    @SerialName("speed")
    val speed:Double,
    @SerialName("deg")
    val deg: Int,
    @SerialName("gust")
    val gust: Double
)

@Serializable
data class CloudsData(
    @SerialName("all")
    val all: Int
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
    val type: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("country")
    val country: String,
    @SerialName("sunrise")
    val sunrise: Long,
    @SerialName("sunset")
    val sunset: Long
)
