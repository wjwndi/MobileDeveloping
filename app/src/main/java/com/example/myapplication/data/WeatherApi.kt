package com.example.myapplication.data

import com.example.myapplication.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast.json")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("key") apiKey: String = API_KEY,
        @Query("days") days: Int = 3,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no",
        @Query("lang") lang: String = "ru",
        ): WeatherDto
}
