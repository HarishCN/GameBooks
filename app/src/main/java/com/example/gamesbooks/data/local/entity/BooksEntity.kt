package com.example.gamesbooks.data.local.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BooksEntity(
    @PrimaryKey(autoGenerate = true)
    val booksId: Long,

    @ColumnInfo
    val charsUrl: ArrayList<String>,

    @ColumnInfo
    val bookName: String,

    @ColumnInfo
    val bookUrl: String,


)