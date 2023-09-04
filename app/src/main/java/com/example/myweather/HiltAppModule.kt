package com.example.myweather

import android.content.Context
import com.example.myweather.database.LocationDatabase
import com.example.myweather.domain.GeocodingApi
import com.example.myweather.domain.WeatherApi
import com.example.myweather.repository.GeocodingRepository
import com.example.myweather.repository.GeocodingRepositoryImpl
import com.example.myweather.repository.LocationRepository
import com.example.myweather.repository.LocationRepositoryImpl
import com.example.myweather.repository.WeatherRepository
import com.example.myweather.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltAppModule {
    @Provides
    @Singleton
    fun provideWeatherRepository(weatherApi: WeatherApi): WeatherRepository {
        return WeatherRepositoryImpl(weatherApi)
    }

    @Provides
    @Singleton
    fun provideGeocodingRepository(geocodingApi: GeocodingApi): GeocodingRepository {
        return GeocodingRepositoryImpl(geocodingApi)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(database: LocationDatabase): LocationRepository {
        return LocationRepositoryImpl(database)
    }

    @Provides
    @Singleton
    fun provideLocationDatabase(@ApplicationContext context: Context): LocationDatabase {
        return LocationDatabase.getInstance(context)
    }
}