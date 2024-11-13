package com.example.myapplication.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CatFactDto(
    @SerializedName("breeds") var breeds: ArrayList<BreedDto> = arrayListOf()
)

data class BreedDto(
    @SerializedName("breed") var breed: String,
    @SerializedName("country") var country: String,
    @SerializedName("origin") var origin: String,
    @SerializedName("coat") var coat: String,
    @SerializedName("pattern") var pattern: String
)