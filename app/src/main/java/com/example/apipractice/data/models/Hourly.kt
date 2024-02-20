package com.example.apipractice.data.models

import com.example.apipractice.domain.models.HourlyWeather

data class Hourly(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    val feels_like: Double,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val temp: Double,
    val uvi: Double,
    val visibility: Int,
    val weather: List<Weather>,
    val wind_deg: Int,
    val wind_gust: Double,
    val wind_speed: Double
)

fun Hourly.toDomain():HourlyWeather{
    return HourlyWeather(
        dt, temp, weather, humidity
    )
}