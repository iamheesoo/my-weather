package com.example.myweather.database

import androidx.room.TypeConverter
import com.example.myweather.data.LatAndLon
import com.google.gson.Gson

class LatAndLonTypeConverter {
    @TypeConverter
    fun latAndLonToJson(latAndLon: LatAndLon): String = Gson().toJson(latAndLon)

    @TypeConverter
    fun jsonToLatAndLon(json: String): LatAndLon = Gson().fromJson(json, LatAndLon::class.java)
}