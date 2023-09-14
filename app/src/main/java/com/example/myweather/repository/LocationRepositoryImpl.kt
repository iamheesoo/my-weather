package com.example.myweather.repository

import com.example.myweather.data.LatAndLon
import com.example.myweather.database.LocationDatabase
import com.example.myweather.database.LocationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocationRepositoryImpl(
    private val locationDatabase: LocationDatabase
) : LocationRepository {
    override suspend fun getLocationData(): Flow<List<LocationEntity>> {
        return flow {
            emit(locationDatabase.locationDao().getLocationData())
        }
    }

    override suspend fun addLocationData(locationEntity: LocationEntity): Flow<Boolean> {
        return flow {
            emit(locationDatabase.locationDao().insertLocationData(locationEntity) != -1L)
        }
    }

    override suspend fun deleteLocationData(latAndLon: LatAndLon): Flow<Boolean> {
        return flow {
            emit(locationDatabase.locationDao().deleteLocationData(latAndLon) == 1)
        }
    }

    override suspend fun updateLocationData(locationEntity: LocationEntity): Flow<Boolean> {
        return flow {
            emit(locationDatabase.locationDao().updateLocationData(locationEntity) != -1)
        }
    }

    override suspend fun insertOrUpdate(locationEntity: LocationEntity): Flow<Boolean> {
        val list = locationDatabase.locationDao().getLocationData()
        val item =
            list.find {
                (it.latAndLon.latitude == locationEntity.latAndLon.latitude && it.latAndLon.longitude == locationEntity.latAndLon.longitude)
                        || (it.name.isNotEmpty() && it.name == locationEntity.name)
            }
        return if (item == null) {
            addLocationData(locationEntity)
        } else {
            updateLocationData(locationEntity)
        }
    }
}