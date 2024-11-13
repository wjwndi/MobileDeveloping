package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.myapplication.data.CatFactApi
import com.example.myapplication.data.CatFactModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val catList = remember {
                    mutableStateOf(listOf<CatFactModel>())
                }

                getData(this, catList)

                MainScreen(
                    catList,
                    onClickSync = {
//                            getData(10, this@MainActivity, catList)
                    }
                )
            }
        }
    }
}

private fun getData(
//    limit: Int,
    context: Context,
    catslist: MutableState<List<CatFactModel>>
) {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://catfact.ninja/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: CatFactApi = retrofit.create(CatFactApi::class.java)

    val job = CoroutineScope(Dispatchers.IO).launch {
        val cats = service.getBreed(100)

        catslist.value = cats.breeds.map{ cat ->
            CatFactModel(
                breed = cat.breed,
                country = cat.country,
                origin = cat.origin,
                coat = cat.coat,
                pattern = cat.pattern
            )
        }
    }
    job
}