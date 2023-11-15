package com.example.myweather.data.repository

import com.example.myweather.BuildConfig
import com.example.myweather.data.response.AirPollutionResponse
import com.example.myweather.data.network.ApiState
import com.example.myweather.data.api.WeatherApi
import com.example.myweather.data.response.WeatherHourlyResponse
import com.example.myweather.data.response.WeatherResponse
import com.example.myweather.data.network.apiCallSerialize
import com.example.myweather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
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