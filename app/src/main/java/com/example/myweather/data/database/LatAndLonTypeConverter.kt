package com.example.myweather.data.database

import androidx.room.TypeConverter
import com.example.myweather.presentation.data.LatAndLon
import com.google.gson.Gson

class LatAndLonTypeConverter {
    @TypeConverter
    fun latAndLonToJson(latAndLon: LatAndLon): String = Gson().toJson(latAndLon)

    @TypeConverter
    fun jsonToLatAndLon(json: String): LatAndLon = Gson().fromJson(json, LatAndLon::class.java)
}