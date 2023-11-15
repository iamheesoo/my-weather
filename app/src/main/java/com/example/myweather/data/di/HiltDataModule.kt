package com.example.myweather.data.di

import android.content.Context
import com.example.myweather.data.api.GeocodingApi
import com.example.myweather.data.api.WeatherApi
import com.example.myweather.data.database.LocationDatabase
import com.example.myweather.data.network.GEOCODING_API_URL
import com.example.myweather.data.network.WEATHER_API_URL
import com.orhanobut.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltApiModule {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor(
                HttpLoggingInterceptor.Logger {
                    try {
                        JSONObject(it)
                        Logger.t("MY_WEATHER_LOGGER").json(it)
                    } catch (e: Exception) {
                        Logger.t("MY_WEATHER_LOGGER").i(it)
                    }
                }
            ).setLevel(HttpLoggingInterceptor.Level.BODY)
        ).build()

    @Provides
    @Singleton
    fun weatherRetrofitCreate(): WeatherApi =
        Retrofit.Builder()
            .baseUrl(WEATHER_API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(WeatherApi::class.java)

    @Provides
    @Singleton
    fun geocodingRetrofitCreate(): GeocodingApi =
        Retrofit.Builder()
            .baseUrl(GEOCODING_API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(GeocodingApi::class.java)
}


@Module
@InstallIn(SingletonComponent::class)
object HiltDatabaseModule {

    @Provides
    @Singleton
    fun provideLocationDatabase(@ApplicationContext context: Context): LocationDatabase {
        return LocationDatabase.getInstance(context)
    }
}