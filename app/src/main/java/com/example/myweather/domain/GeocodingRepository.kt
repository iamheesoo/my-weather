package com.example.myweather.domain

import kotlinx.coroutines.flow.Flow

interface GeocodingRepository {
    suspend fun getGeocoding(city: String): Flow<ApiState<List<GeocodingData>>>
}