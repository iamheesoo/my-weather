package com.example.myweather.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationDao {
    @Query("SELECT * FROM location_table")
    fun getLocationData(): List<LocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocationData(data: LocationEntity): Long

    @Query("DELETE FROM location_table where id=:id")
    fun deleteLocationData(id: Int): Int
}