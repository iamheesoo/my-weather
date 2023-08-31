package com.example.myweather.repository

import com.example.myweather.domain.ApiState
import com.example.myweather.domain.GeocodingData
import kotlinx.coroutines.flow.Flow

interface GeocodingRepository {
    suspend fun getGeocoding(city: String): Flow<ApiState<List<GeocodingData>>>
}