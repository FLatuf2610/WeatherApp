package com.example.apipractice.data.models

import com.example.apipractice.domain.models.WeatherState
import com.google.gson.annotations.SerializedName

data class WeatherApiResponse(
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    @SerializedName("timezone_offset")val timezoneOffset: Int
)

fun WeatherApiResponse.toDomain():WeatherState {
    return WeatherState(
        timezone = timezone,
        current = current.toDomain(),
        hourly =  hourly.map { it.toDomain() },
        daily = daily.map { it.toDomain() }

    )
}