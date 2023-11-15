package com.example.myweather.data.network

import com.example.myweather.data.api.GeocodingApi
import com.example.myweather.data.api.WeatherApi
import com.orhanobut.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
object ApiService {
    private const val WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/"
    private const val GEOCODING_API_URL = "https://api.openweathermap.org/geo/1.0/"

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

    private val weatherRetrofit: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(WEATHER_API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(WeatherApi::class.java)
    }

    private val geocodingRetrofit: GeocodingApi by lazy {
        Retrofit.Builder()
            .baseUrl(GEOCODING_API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(GeocodingApi::class.java)
    }

    @Provides
    @Singleton
    fun weatherRetrofitCreate(): WeatherApi = weatherRetrofit

    @Provides
    @Singleton
    fun geocodingRetrofitCreate(): GeocodingApi = geocodingRetrofit
}