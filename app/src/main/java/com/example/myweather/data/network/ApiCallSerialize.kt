package com.example.myweather.data.network

import com.orhanobut.logger.Logger
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.Response

suspend inline fun <reified T : Any> apiCallSerialize(
    call: suspend () -> Response<String>
): ApiState<T> {
    return try {
        val json = Json {
            encodeDefaults = true
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
        val response = call.invoke()
        val responseStr = response.body() ?: ""

        if (response.isSuccessful) {
            ApiState.Success(json.decodeFromString<T>(responseStr))
        } else {
            ApiState.Error(message = response.errorBody()?.toString() ?: "response error")
        }

    } catch (e: Exception) {
        Logger.d("${e.cause} \n${e.message}")
        ApiState.Error(message = e.message)
    }
}