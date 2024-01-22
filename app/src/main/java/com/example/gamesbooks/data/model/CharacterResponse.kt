package com.example.gamesbooks.data.model

import com.example.gamesbooks.data.local.entity.CharactersEntity

data class CharacterResponse(
    val url: String,
    val name: String,
    val aliases: ArrayList<String>,
    val gender: String,
    val titles: ArrayList<String>
)

fun CharacterResponse.toCharacter() = CharactersEntity(
    charId = 0,
    charUrl = url,
    charName = name,
    alias = aliases,
    charGender = gender,
    titles = titles,
)