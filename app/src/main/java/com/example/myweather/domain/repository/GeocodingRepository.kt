package com.example.myweather.domain.repository

import com.example.myweather.data.network.ApiState
import com.example.myweather.data.response.GeocodingData
import kotlinx.coroutines.flow.Flow

interface GeocodingRepository {
    suspend fun getGeocoding(city: String): Flow<ApiState<List<GeocodingData>>>
}