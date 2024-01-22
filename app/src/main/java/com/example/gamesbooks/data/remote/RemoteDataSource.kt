package com.example.gamesbooks.data.remote

import com.example.gamesbooks.data.model.BooksResponse
import com.example.gamesbooks.data.model.CharacterResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) : NetworkRequest() {

    suspend fun getBooks(): ResponseState<List<BooksResponse>> {
        return invokeApiRequest(apiCall = {
            apiService.getBooks()
        })
    }

    suspend fun getCharacterDetails(url: String): ResponseState<CharacterResponse> {
        return invokeApiRequest(apiCall = {
            apiService.getCharacterList(url)
        })
    }
}