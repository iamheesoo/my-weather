package com.example.myweather.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myweather.data.LatAndLon

@Entity(tableName = "location_table")
data class LocationEntity(
    @PrimaryKey
    val latAndLon: LatAndLon = LatAndLon(),
    val name: String = "",
    val temp: Int = 0,
    val tempMax: Int = 0,
    val tempMin: Int = 0,
    val weatherInfo: String = ""
)