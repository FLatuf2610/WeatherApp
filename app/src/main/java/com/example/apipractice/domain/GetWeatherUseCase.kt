package com.example.apipractice.domain

import android.location.Location
import com.example.apipractice.data.Repository
import com.example.apipractice.data.models.WeatherApiResponse
import com.example.apipractice.data.models.toDomain
import com.example.apipractice.domain.models.WeatherState
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val repository: Repository){
    suspend operator fun invoke(location: Location) : WeatherState? {
        val response = repository.getWeather(location)
        if (response != null){
            return response.toDomain()
        }
        return null
    }
}