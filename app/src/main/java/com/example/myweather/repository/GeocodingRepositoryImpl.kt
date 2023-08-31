package com.example.myweather.repository

import com.example.myweather.BuildConfig
import com.example.myweather.domain.ApiState
import com.example.myweather.domain.GeocodingApi
import com.example.myweather.domain.GeocodingData
import com.example.myweather.domain.apiCallSerialize
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GeocodingRepositoryImpl(private val geocodingApi: GeocodingApi) : GeocodingRepository {
    override suspend fun getGeocoding(city: String): Flow<ApiState<List<GeocodingData>>> {
        return flow {
            val map = hashMapOf<String, String>().apply {
                this["q"] = city
                this["limit"] = "5"
                this["appid"] = BuildConfig.WEATHER_API_KEY
            }
            emit(
                apiCallSerialize {
                    geocodingApi.getCoordinate(map)
                }
            )
        }
    }
}