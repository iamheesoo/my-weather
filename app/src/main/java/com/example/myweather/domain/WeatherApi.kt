package com.example.myweather.domain

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
}