package com.example.myweather

import android.content.Context
import com.example.myweather.data.database.LocationDatabase
import com.example.myweather.data.api.GeocodingApi
import com.example.myweather.data.api.WeatherApi
import com.example.myweather.domain.repository.GeocodingRepository
import com.example.myweather.data.repository.GeocodingRepositoryImpl
import com.example.myweather.domain.repository.LocationRepository
import com.example.myweather.data.repository.LocationRepositoryImpl
import com.example.myweather.domain.repository.WeatherRepository
import com.example.myweather.data.repository.WeatherRepositoryImpl
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