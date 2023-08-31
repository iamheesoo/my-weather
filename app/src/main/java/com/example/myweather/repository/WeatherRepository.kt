package com.example.myweather.repository

import com.example.myweather.domain.AirPollutionResponse
import com.example.myweather.domain.ApiState
import com.example.myweather.domain.WeatherHourlyResponse
import com.example.myweather.domain.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): Flow<ApiState<WeatherResponse>>

    suspend fun getWeatherHourly(lat: Double, lon: Double): Flow<ApiState<WeatherHourlyResponse>>

    suspend fun getAirPollution(lat: Double, lon: Double): Flow<ApiState<AirPollutionResponse>>
}