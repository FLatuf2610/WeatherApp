package com.example.apipractice.data

import android.location.Location
import android.util.Log
import com.example.apipractice.data.models.WeatherApiResponse
import com.example.apipractice.data.network.WeatherApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(private val weatherApiClient: WeatherApiClient) {
    suspend fun getWeather(location :Location) : WeatherApiResponse? {
        return withContext(Dispatchers.IO){
            try {
                val response = weatherApiClient.getWeather(location.latitude,location.longitude)
                Log.println(Log.ASSERT, "API RESPONDED SOMETHING", response.timezone)
                response
            }catch (e:Exception){
                Log.println(Log.ASSERT, "API CALL FAILED, NOTIFY USER", e.message!!)
                null
            }
        }
    }
}