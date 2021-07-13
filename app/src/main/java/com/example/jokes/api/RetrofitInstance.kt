package com.example.jokes.api

import com.example.jokes.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val jokeWebservice: JokeWebservice by lazy {
        retrofitInstance.create(JokeWebservice::class.java)
    }
}