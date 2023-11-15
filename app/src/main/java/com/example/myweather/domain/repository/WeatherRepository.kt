package com.example.myweather.domain.repository

import com.example.myweather.data.response.AirPollutionResponse
import com.example.myweather.data.network.ApiState
import com.example.myweather.data.response.WeatherHourlyResponse
import com.example.myweather.data.response.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): Flow<ApiState<WeatherResponse>>

    suspend fun getWeatherHourly(lat: Double, lon: Double): Flow<ApiState<WeatherHourlyResponse>>

    suspend fun getAirPollution(lat: Double, lon: Double): Flow<ApiState<AirPollutionResponse>>
}