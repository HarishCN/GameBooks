package com.example.gamesbooks.data.local.dao

import androidx.room.*
import com.example.gamesbooks.data.local.entity.CharactersEntity

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characters: CharactersEntity)


    @Query("SELECT * FROM CharactersEntity WHERE charUrl=:url")
    fun getCharacterData(url: String): CharactersEntity


    @Query("SELECT EXISTS (SELECT 1 FROM CharactersEntity WHERE charUrl = :url)")
    fun isUrlAdded(url: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characters: List<CharactersEntity>)

    @Update
    fun updateCharacter(char: CharactersEntity)
}