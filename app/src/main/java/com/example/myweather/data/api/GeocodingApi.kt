package com.example.myweather.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GeocodingApi {
    @GET("direct")
    suspend fun getCoordinate(@QueryMap map: Map<String, String>): Response<String>
}