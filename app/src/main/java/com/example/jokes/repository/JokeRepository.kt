package com.example.jokes.repository

import com.example.jokes.api.RetrofitInstance

object JokeRepository {
    suspend fun getJokesList(number: Int) =
        RetrofitInstance.jokeWebservice.getJokesList(number)
}