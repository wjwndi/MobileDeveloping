package com.example.myapplication.data

import retrofit2.http.GET
import retrofit2.http.Query

interface CatFactApi {
    @GET("breeds")
    suspend fun getBreed(
        @Query("limit") limit: Int
    ): CatFactDto
}