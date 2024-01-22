package com.example.gamesbooks.di

import android.content.Context
import androidx.room.Room
import com.example.gamesbooks.data.local.database.AppDatabase
import com.example.gamesbooks.data.local.dao.BookDao
import com.example.gamesbooks.data.local.dao.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideBooksDao(appDatabase: AppDatabase): BookDao {
        return appDatabase.booksDao()
    }

    @Provides
    fun provideCharsDao(appDatabase: AppDatabase): CharacterDao {
        return appDatabase.charsDao()
    }

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "books.db"
        ).build()
    }
}