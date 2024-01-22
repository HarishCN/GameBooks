package com.example.gamesbooks.domain

import com.example.gamesbooks.data.local.entity.CharactersEntity
import com.example.gamesbooks.data.remote.ResponseState
import com.example.gamesbooks.data.repository.BooksRepository

class GetCharacterUseCase (private val repository: BooksRepository) {

    suspend operator fun invoke(charUrl: String): ResponseState<CharactersEntity> {
        // You can add additional logic here if needed
        return repository.getCharacter(charUrl)
    }
}