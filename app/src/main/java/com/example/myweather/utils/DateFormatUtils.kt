package com.example.myweather.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.orhanobut.logger.Logger
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

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
    val sdf = SimpleDateFormat("yyy-MM-dd hh:mm:ss")
    val result = kotlin.runCatching {
        sdf.parse(this)
    }
        .mapCatching { it.time }
        .onFailure { Logger.e(it, it.stackTraceToString(), it) }
    return result.getOrDefault(0L)
}

fun Long.getUTCtoKST(): String {
    val utcDate = Date(this * 1000)
    val sdf = SimpleDateFormat("HH:mm")
    val kstTimeZone = TimeZone.getTimeZone("Asia/Seoul")
    sdf.timeZone = kstTimeZone
    return sdf.format(utcDate)
}

fun Long.getCurrentTime(): String {
    val pattern = "HH:mm"
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val nowUTC = OffsetDateTime.now(ZoneOffset.UTC)
        val later = nowUTC.plusSeconds(this)
        later.format(DateTimeFormatter.ofPattern(pattern))
    } else {
        val calendar = Calendar.getInstance().apply {
            timeZone = TimeZone.getTimeZone("UTC")
            add(Calendar.HOUR, this@getCurrentTime.toInt())
        }
        val sdf = SimpleDateFormat(pattern)
        sdf.format(calendar.time)
    }
}