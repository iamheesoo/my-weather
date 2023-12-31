package com.example.myweather.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @QueryMap map: Map<String, String>
    ): Response<String>

    @GET("forecast")
    suspend fun getWeatherHourly(
        @QueryMap map: Map<String, String>
    ): Response<String>

    @GET("air_pollution")
    suspend fun getAirPollution(
        @QueryMap map: Map<String, String>
    ): Response<String>
}