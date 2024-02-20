package com.example.apipractice.data.models

import com.example.apipractice.domain.models.CurrentWeather

data class Current(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val uvi: Double,
    val visibility: Int,
    val weather: List<Weather>,
    val wind_deg: Int,
    val wind_gust: Double,
    val wind_speed: Double
)

fun Current.toDomain():CurrentWeather {
    return CurrentWeather(
        dt = dt,
        feels_like = feels_like,
        humidity = humidity,
        temp = temp,
        uvi = uvi,
        weather = weather,
        wind_speed = wind_speed
    )
}