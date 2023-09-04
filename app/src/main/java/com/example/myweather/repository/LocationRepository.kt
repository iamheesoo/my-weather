package com.example.myweather.repository

import com.example.myweather.database.LocationEntity
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    suspend fun getLocationData(): Flow<List<LocationEntity>>

    suspend fun addLocationData(LocationEntity: LocationEntity): Flow<Boolean>

    suspend fun deleteLocationData(id: Int): Flow<Boolean>
}