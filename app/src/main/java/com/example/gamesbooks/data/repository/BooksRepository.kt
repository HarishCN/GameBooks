package com.example.gamesbooks.data.repository

import com.example.gamesbooks.data.local.entity.BooksEntity
import com.example.gamesbooks.data.local.entity.CharactersEntity
import com.example.gamesbooks.data.remote.ResponseState


interface BooksRepository {
    suspend fun getBooks(): ResponseState<List<BooksEntity>>
    suspend fun getCharacter(charUrl: String): ResponseState<CharactersEntity>
}