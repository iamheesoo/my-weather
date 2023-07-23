package com.example.myweather.domain

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherApi {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @QueryMap map: Map<String, String>
    ): Response<String>
}