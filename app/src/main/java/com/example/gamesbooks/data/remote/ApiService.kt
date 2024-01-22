package com.example.gamesbooks.data.remote

import com.example.gamesbooks.data.model.BooksResponse
import com.example.gamesbooks.data.model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET("books")
    suspend fun getBooks(): Response<List<BooksResponse>>

    @GET
    suspend fun getCharacterList(@Url url: String): Response<CharacterResponse>
}