package com.example.gamesbooks.data.local.dao

import androidx.room.*
import com.example.gamesbooks.data.local.entity.BooksEntity

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(books: List<BooksEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(books: BooksEntity)

    @Query("SELECT COUNT(*) from BooksEntity")
    fun booksCounts(): Int

    @Query("SELECT * FROM BooksEntity")
    fun getAllItems(): List<BooksEntity>
}