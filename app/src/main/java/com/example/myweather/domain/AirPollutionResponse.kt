package com.example.myweather.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirPollutionResponse(
    @SerialName("coord")
    val coord: CoordData? = null,
    @SerialName("list")
    val list: List<AirPollution>? = null
)

@Serializable
data class AirPollution(
    @SerialName("dt")
    val dt: Long? = null,
    @SerialName("main")
    val main: AirPollutionMain? = null,
    @SerialName("components")
    val components: Components? = null
)

@Serializable
data class AirPollutionMain(
    @SerialName("aqi")
    val aqi: Int? = null
)

@Serializable
data class Components(
    @SerialName("co")
    val co: Double? = null,
    @SerialName("no")
    val no: Double? = null,
    @SerialName("no2")
    val no2: Double? = null,
    @SerialName("o3")
    val o3: Double? = null,
    @SerialName("so2")
    val so2: Double? = null,
    @SerialName("pm2_5")
    val pm2_5: Double? = null,
    @SerialName("pm10")
    val pm10: Double? = null,
    @SerialName("nh3")
    val nh3: Double? = null
)
