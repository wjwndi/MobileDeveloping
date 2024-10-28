package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.myapplication.data.WeatherModel
import com.example.myapplication.data.WeatherApi
import com.example.myapplication.scenes.DialogSearch
import com.example.myapplication.scenes.MainCard
import com.example.myapplication.scenes.TabLayout
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val API_KEY = "e9cca3373a794efe9e6135059242810"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val daysList = remember {
                    mutableStateOf(listOf<WeatherModel>())
                }
                val dialogState = remember {
                    mutableStateOf(false)
                }
                val currentDay = remember {
                    mutableStateOf(
                        WeatherModel(
                            "",
                            "",
                            "0.0",
                            "",
                            "",
                            "0.0",
                            "0.0",
                            listOf()
                        )
                    )
                }

                if (dialogState.value) DialogSearch(dialogState, onSubmit = {
                    getData(
                        it, this@MainActivity,
                        daysList, currentDay
                    )
                })

                getData("Коломна", this, daysList, currentDay)

                Image(
                    painter = painterResource(
                        id = R.drawable.weather_bg
                    ),
                    contentDescription = "im1",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.5f),
                    contentScale = ContentScale.FillBounds
                )
                Column {
                    MainCard(currentDay,
                        onClickSync = {
                            getData(
                                "Коломна", this@MainActivity,
                                daysList, currentDay
                            )
                        },
                        onClickSearch = {
                            dialogState.value = true
                        })
                    TabLayout(daysList, currentDay)
                }
            }
        }
    }
}

private fun getData(
    city: String, context: Context,
    daysList: MutableState<List<WeatherModel>>,
    currentDay: MutableState<WeatherModel>
) {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: WeatherApi = retrofit.create(WeatherApi::class.java)

    val job = CoroutineScope(Dispatchers.IO).launch {
        val weather = service.getWeather(city)

        daysList.value = weather.forecast.forecastday.map { forecastDay ->
            WeatherModel(
                city = weather.location.name,
                time = forecastDay.date,
                currentTemp = "",
                condition = forecastDay.day.condition.text,
                icon = forecastDay.day.condition.icon,
                maxTemp = forecastDay.day.maxTemp.toFloat().toInt().toString() + "°C",
                minTemp = forecastDay.day.minTemp.toFloat().toInt().toString() + "°C",
                hours = forecastDay.hour
            )
        }

        currentDay.value = daysList.value.first().copy(
            time = weather.current.time,
            currentTemp = weather.current.currentTemp.toFloat().toInt().toString() + "°C",
        )
    }
    job
}


private fun getWeatherByDays(response: String): List<WeatherModel> {
    if (response.isEmpty()) return listOf()

    val list = ArrayList<WeatherModel>()
    val mainObject = JSONObject(response)
    val city = mainObject.getJSONObject("location")
        .getString("name")
    val days = mainObject.getJSONObject("forecast")
        .getJSONArray("forecastday")

    for (i in 0 until days.length()) {
        val item = days[i] as JSONObject
        list.add(
            WeatherModel(
                city,
                item.getString("date"),
                "",
                item.getJSONObject("day")
                    .getJSONObject("condition")
                    .getString("text"),
                item.getJSONObject("day")
                    .getJSONObject("condition")
                    .getString("icon"),
                item.getJSONObject("day")
                    .getString("maxtemp_c")
                    .toFloat().toInt().toString() + "°C",
                item.getJSONObject("day")
                    .getString("mintemp_c")
                    .toFloat().toInt().toString() + "°C",
                listOf()
            )
        )
    }
    list[0] = list[0].copy(
        time = mainObject.getJSONObject("current")
            .getString("last_updated"),
        currentTemp = mainObject.getJSONObject("current")
            .getString("temp_c")
    )
    return list
}