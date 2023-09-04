package com.example.myweather.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [LocationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LocationDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var instance: LocationDatabase? = null

        fun getInstance(context: Context): LocationDatabase {
            return instance ?: synchronized(this) {
                buildDatabase(context)
                    .also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): LocationDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                LocationDatabase::class.java,
                "task_database"
            )
                .fallbackToDestructiveMigration() // migration이 실패하는 경우 db를 재생성 (모든 데이터가 유실될 수 있음)
                .build()
        }
    }

    abstract fun locationDao(): LocationDao
}