package com.example.gamesbooks.data.local.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharactersEntity(
    @PrimaryKey(autoGenerate = true)
    val charId: Long,

    @ColumnInfo
    val charGender: String,

    @ColumnInfo
    val titles: ArrayList<String>,

    @ColumnInfo
    val charUrl: String,

    @ColumnInfo
    val charName: String,

    @ColumnInfo
    val alias: ArrayList<String>
)