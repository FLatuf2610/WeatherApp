package com.example.apipractice.domain.models

import com.example.apipractice.data.models.Weather

data class CurrentWeather(
    val dt: Int,
    val feels_like: Double,
    val humidity: Int,
    val temp: Double,
    val uvi: Double,
    val weather: List<Weather>,
    val wind_speed: Double
)
