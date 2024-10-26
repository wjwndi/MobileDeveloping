package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.components.InfoScreen
import com.example.myapplication.ui.components.MainScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.utils.ListItem
import com.example.myapplication.utils.Routes
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            var item: ListItem? = null
            MyApplicationTheme {
                NavHost(
                    navController = navController,
                    startDestination = Routes.MAIN_SCREEN){
                    composable(Routes.MAIN_SCREEN){
                        MainScreen{ listitem ->
                            item = listitem
                            navController.navigate(Routes.INFO_SCREEN)
                        }
                    }
                    composable(Routes.INFO_SCREEN) {
                        InfoScreen(item = item!!)
                    }
                }
            }
        }
    }
}

