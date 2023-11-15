package com.example.myweather.presentation.utils

import com.orhanobut.logger.Logger
import org.threeten.bp.Instant
import org.threeten.bp.LocalTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
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
    val nowUTC = OffsetDateTime.now(ZoneOffset.UTC)
    val later = nowUTC.plusSeconds(this)
    return later.format(DateTimeFormatter.ofPattern(pattern))
}

fun convertUnixTimestampToUTC(timestamp: Long): String {
    val instant = Instant.ofEpochSecond(timestamp)
    val timezone = ZoneId.of("UTC")
    val localDateTime = instant.atZone(ZoneId.of(timezone.id)).toLocalDateTime()

    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return localDateTime.format(formatter)
}

fun compareTimes(time1: String, time2: String): Int {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val localTime1 = LocalTime.parse(time1, formatter)
    val localTime2 = LocalTime.parse(time2, formatter)

    // 크기 비교
    return localTime1.compareTo(localTime2)
}