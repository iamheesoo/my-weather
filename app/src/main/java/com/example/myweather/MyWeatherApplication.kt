package com.example.myweather

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyWeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true)
            .methodCount(2)
            .methodOffset(0)
            .tag("MY_WEATHER_LOGGER")
            .build()

        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {})
    }
}