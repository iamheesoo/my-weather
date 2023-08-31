package com.example.myweather.repository

import com.example.myweather.BuildConfig
import com.example.myweather.domain.AirPollutionResponse
import com.example.myweather.domain.ApiState
import com.example.myweather.domain.WeatherApi
import com.example.myweather.domain.WeatherHourlyResponse
import com.example.myweather.domain.WeatherResponse
import com.example.myweather.domain.apiCallSerialize
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
                this["units"] = "metric"
            }
            emit(
                apiCallSerialize {
                    weatherApi.getWeather(map)
                }
            )
        }
    }

    override suspend fun getWeatherHourly(
        lat: Double,
        lon: Double
    ): Flow<ApiState<WeatherHourlyResponse>> {
        return flow {
            val map = hashMapOf<String, String>().apply {
                this["lat"] = lat.toString()
                this["lon"] = lon.toString()
                this["appid"] = BuildConfig.WEATHER_API_KEY
                this["lang"] = "kr"
                this["units"] = "metric"
                this["cnt"] = "20"
            }
            emit(
                apiCallSerialize {
                    weatherApi.getWeatherHourly(map)
                }
            )
        }
    }

    override suspend fun getAirPollution(
        lat: Double,
        lon: Double
    ): Flow<ApiState<AirPollutionResponse>> {
        return flow {
            val map = hashMapOf<String, String>().apply {
                this["lat"] = lat.toString()
                this["lon"] = lon.toString()
                this["appid"] = BuildConfig.WEATHER_API_KEY
            }
            emit(
                apiCallSerialize {
                    weatherApi.getAirPollution(map)
                }
            )
        }
    }
}