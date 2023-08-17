package com.example.myweather.utils

import com.orhanobut.logger.Logger
import java.text.SimpleDateFormat

fun String.dtTxtToHour(): String? {
    // format 2022-08-30 18:00:00
    val result = kotlin.runCatching {
        this.split(" ")[1].split(":")[0]
    }
        .onSuccess { it }
        .onFailure { Logger.e(it, it.stackTraceToString(), it) }
    return result.getOrNull()
}

fun String.dtTxtToLong(): Long {
    val sdf = SimpleDateFormat("yyy-MM-dd HH:mm:ss")
    val result = kotlin.runCatching {
        sdf.parse(this)
    }
        .mapCatching { it.time }
        .onFailure { Logger.e(it, it.stackTraceToString(), it) }
    return result.getOrDefault(0L)
}