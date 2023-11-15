package com.example.myweather.data.repository

import com.example.myweather.BuildConfig
import com.example.myweather.data.network.ApiState
import com.example.myweather.data.api.GeocodingApi
import com.example.myweather.data.response.GeocodingData
import com.example.myweather.data.network.apiCallSerialize
import com.example.myweather.domain.repository.GeocodingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GeocodingRepositoryImpl @Inject constructor(private val geocodingApi: GeocodingApi) : GeocodingRepository {
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