package com.example.apipractice.data.network

import com.example.apipractice.common.Constants
import com.example.apipractice.data.models.WeatherApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiClient {
    @GET("onecall")
    suspend fun getWeather(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("exclude") exclude:String = "minutely,alerts",
        @Query("units") units:String = "metric",
        @Query("appid") apiId:String = Constants.API_KEY
    ): WeatherApiResponse
}
