package com.example.gamesbooks.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gamesbooks.data.local.dao.BookDao
import com.example.gamesbooks.data.local.dao.CharacterDao
import com.example.gamesbooks.data.local.entity.BooksEntity
import com.example.gamesbooks.data.local.entity.CharactersEntity
import com.example.gamesbooks.util.ArrayListConverter

@Database(entities = [(BooksEntity::class), (CharactersEntity::class)], version = 1)
@TypeConverters(ArrayListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun booksDao(): BookDao
    abstract fun charsDao(): CharacterDao
}