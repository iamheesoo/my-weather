package com.example.myweather.domain

import com.orhanobut.logger.Logger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object ApiService {
    private const val API_URL = "https://api.openweathermap.org/data/2.5/"

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

    private val client: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(WeatherApi::class.java)
    }

    fun create(): WeatherApi = client
}