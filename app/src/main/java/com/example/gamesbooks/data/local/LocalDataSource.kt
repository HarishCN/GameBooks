package com.example.gamesbooks.data.local

import com.example.gamesbooks.data.local.dao.BookDao
import com.example.gamesbooks.data.local.dao.CharacterDao
import com.example.gamesbooks.data.local.entity.CharactersEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val bookDao: BookDao,
    private val characterDao: CharacterDao
) {
    fun insertCharacter(characters: CharactersEntity) {
        characterDao.insert(characters)
    }

    fun isUrlAdded(url: String): Boolean {
        return characterDao.isUrlAdded(url)
    }

    fun getCharacter(url: String): CharactersEntity {
        return characterDao.getCharacterData(url)
    }
}