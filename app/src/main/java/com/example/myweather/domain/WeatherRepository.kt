package com.example.myweather.domain

import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): Flow<ApiState<WeatherResponse>>

    suspend fun getWeatherHourly(lat: Double, lon: Double): Flow<ApiState<WeatherHourlyResponse>>

    suspend fun getAirPollution(lat: Double, lon: Double): Flow<ApiState<AirPollutionResponse>>
}