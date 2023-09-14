package com.example.myweather.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myweather.data.LatAndLon

@Dao
interface LocationDao {
    @Query("SELECT * FROM location_table")
    fun getLocationData(): List<LocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocationData(data: LocationEntity): Long

    @Query("DELETE FROM location_table where latAndLon=:latAndLon")
    fun deleteLocationData(latAndLon: LatAndLon): Int

    @Update
    fun updateLocationData(data: LocationEntity): Int
}