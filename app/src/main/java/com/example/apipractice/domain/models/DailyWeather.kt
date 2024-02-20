package com.example.apipractice.domain.models

import com.example.apipractice.data.models.Temp
import com.example.apipractice.data.models.Weather

data class DailyWeather(
    val dt: Int,
    val temp: Temp,
    val weather: List<Weather>,

    )