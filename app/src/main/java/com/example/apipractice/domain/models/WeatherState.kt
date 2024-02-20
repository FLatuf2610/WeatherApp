package com.example.apipractice.domain.models

data class WeatherState(
    val current: CurrentWeather,
    val timezone: String,
    val daily: List<DailyWeather>,
    val hourly: List<HourlyWeather>,
)