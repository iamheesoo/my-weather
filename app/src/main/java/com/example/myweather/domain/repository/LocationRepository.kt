package com.example.myweather.domain.repository

import com.example.myweather.presentation.data.LatAndLon
import com.example.myweather.domain.local.LocationEntity
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    suspend fun getLocationData(): Flow<List<LocationEntity>>

    suspend fun addLocationData(locationEntity: LocationEntity): Flow<Boolean>

    suspend fun deleteLocationData(latAndLon: LatAndLon): Flow<Boolean>

    suspend fun updateLocationData(locationEntity: LocationEntity): Flow<Boolean>

    suspend fun insertOrUpdate(locationEntity: LocationEntity): Flow<Boolean>
}