package com.example.myweather.repository

import com.example.myweather.database.LocationDatabase
import com.example.myweather.database.LocationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocationRepositoryImpl(
    private val LocationDatabase: LocationDatabase
) : LocationRepository {
    override suspend fun getLocationData(): Flow<List<LocationEntity>> {
        return flow {
            emit(LocationDatabase.locationDao().getLocationData())
        }
    }

    override suspend fun addLocationData(LocationEntity: LocationEntity): Flow<Boolean> {
        return flow {
            emit(LocationDatabase.locationDao().insertLocationData(LocationEntity) !=-1L )
        }
    }

    override suspend fun deleteLocationData(id: Int): Flow<Boolean> {
        return flow {
            emit(LocationDatabase.locationDao().deleteLocationData(id) == 1)
        }
    }
}