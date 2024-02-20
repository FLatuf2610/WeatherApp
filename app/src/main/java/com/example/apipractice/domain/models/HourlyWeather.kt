package com.example.apipractice.domain.models

import com.example.apipractice.data.models.Weather

data class HourlyWeather(
    val dt: Int,
    val temp: Double,
    val weather: List<Weather>,
    val humidity: Int
)
