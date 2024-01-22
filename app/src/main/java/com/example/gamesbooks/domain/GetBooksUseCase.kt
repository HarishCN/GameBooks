package com.example.gamesbooks.domain

import com.example.gamesbooks.data.local.entity.BooksEntity
import com.example.gamesbooks.data.remote.ResponseState
import com.example.gamesbooks.data.repository.BooksRepository

class GetBooksUseCase (private val repository: BooksRepository) {

    suspend operator fun invoke(): ResponseState<List<BooksEntity>> {
        // You can add additional logic here if needed
        return repository.getBooks()
    }
}
