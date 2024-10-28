package com.example.myapplication.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherDto(
    @SerializedName("location") var location: LocationDto,
    @SerializedName("current") var current: CurrentDto,
    @SerializedName("forecast") var forecast: ForecastDto
) : Serializable

data class LocationDto(
    @SerializedName("name") var name: String,
)
data class ForecastDto (
    @SerializedName("forecastday" ) var forecastday : ArrayList<ForecastDayDto> = arrayListOf()
)
data class ForecastDayDto(
    @SerializedName("date") var date: String,
    @SerializedName("day") var day: DayDto,
    @SerializedName("hour") var hour: ArrayList<HourDto> = arrayListOf()
) : Serializable


data class DayDto(
    @SerializedName("maxtemp_c") var maxTemp: Double,
    @SerializedName("mintemp_c") var minTemp: Double,
    @SerializedName("condition") var condition: ConditionDto,
)

data class ConditionDto(
    @SerializedName("text") var text: String,
    @SerializedName("icon") var icon: String
)

data class HourDto(
    @SerializedName("time") var time: String,
    @SerializedName("temp_c") var tempC: Double,
    @SerializedName("condition") var condition: ConditionDto,
)

data class CurrentDto(
    @SerializedName("last_updated") var time: String,
    @SerializedName("temp_c") var currentTemp: Double,
)

