package com.example.jokes.api

import com.example.jokes.models.JokesList
import com.example.jokes.util.Constants.ESCAPE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JokeWebservice {
    @GET("/jokes/random/{number}")
    suspend fun getJokesList(
        @Path("number") number: Int,
        @Query("escape") escape: String = ESCAPE
    ): Response<JokesList>
}