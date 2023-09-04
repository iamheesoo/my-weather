package com.example.myweather.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeocodingData(
    @SerialName("name")
    val name: String? = null,
    @SerialName("lat")
    val lat: Double? = null,
    @SerialName("lon")
    val lon: Double? = null,
    @SerialName("country")
    val country: String? = null,
    @SerialName("state")
    val state: String? = null,
    @SerialName("local_names")
    val localNames: LocalNames? = null
)

@Serializable
data class LocalNames(
    @SerialName("id")
    val id: String? = null,
    @SerialName("ko")
    val ko: String? = null,
    @SerialName("en")
    val en: String? = null
)
