package com.example.myweather

import com.example.myweather.domain.GeocodingApi
import com.example.myweather.domain.WeatherApi
import com.example.myweather.repository.GeocodingRepository
import com.example.myweather.repository.GeocodingRepositoryImpl
import com.example.myweather.repository.WeatherRepository
import com.example.myweather.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}