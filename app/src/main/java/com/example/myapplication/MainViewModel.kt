package com.example.myapplication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val isHomeClicked = mutableStateOf(true)
    val isListClicked = mutableStateOf(false)

    fun onHomeClick() {
        isHomeClicked.value = true
        isListClicked.value = false
    }
    fun onListClick() {
        isHomeClicked.value = false
        isListClicked.value = true
    }
}