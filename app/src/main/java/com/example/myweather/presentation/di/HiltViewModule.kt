package com.example.myweather.presentation.di

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
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HiltViewModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindGeocodingRepository(impl: GeocodingRepositoryImpl): GeocodingRepository

    @Binds
    @Singleton
    abstract fun bindLocationRepository(impl: LocationRepositoryImpl): LocationRepository

}