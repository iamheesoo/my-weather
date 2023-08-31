package com.example.myweather.domain

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GeocodingApi {
    @GET("direct")
    suspend fun getCoordinate(@QueryMap map: Map<String, String>): Response<String>
}