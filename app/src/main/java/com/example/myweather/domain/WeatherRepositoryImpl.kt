package com.example.myweather.domain

import com.example.myweather.BuildConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi
) : WeatherRepository {
    override suspend fun getWeather(lat: Double, lon: Double): Flow<ApiState<WeatherResponse>> {
        return flow {
            val map = hashMapOf<String, String>().apply {
                this["lat"] = lat.toString()
                this["lon"] = lon.toString()
                this["appid"] = BuildConfig.WEATHER_API_KEY
                this["lang"] = "kr"
            }
            emit(
                apiCallSerialize {
                    weatherApi.getWeather(map)
                }
            )
        }
    }
}