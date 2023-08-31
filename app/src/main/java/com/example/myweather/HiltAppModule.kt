package com.example.myweather

import com.example.myweather.domain.GeocodingApi
import com.example.myweather.domain.GeocodingRepository
import com.example.myweather.domain.GeocodingRepositoryImpl
import com.example.myweather.domain.WeatherApi
import com.example.myweather.domain.WeatherRepository
import com.example.myweather.domain.WeatherRepositoryImpl
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