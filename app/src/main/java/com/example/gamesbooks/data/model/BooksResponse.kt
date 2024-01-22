package com.example.gamesbooks.data.model

import com.example.gamesbooks.data.local.entity.BooksEntity

data class BooksResponse(val name: String, val url: String, val characters: ArrayList<String>)

fun BooksResponse.toBook() = BooksEntity(
    booksId = 0,
    bookUrl = url,
    bookName = name,
    charsUrl = characters
)