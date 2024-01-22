package com.example.gamesbooks.di

import com.example.gamesbooks.data.local.LocalDataSource
import com.example.gamesbooks.data.remote.ApiService
import com.example.gamesbooks.data.remote.RemoteDataSource
import com.example.gamesbooks.data.repository.BooksRepository
import com.example.gamesbooks.data.repository.BooksRepositoryImpl
import com.example.gamesbooks.domain.GetBooksUseCase
import com.example.gamesbooks.domain.GetCharacterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// AppModule.kt
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource {
        return RemoteDataSource(apiService)
    }

    @Singleton
    @Provides
    fun provideBooksRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): BooksRepository {
        return BooksRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Provides
    fun provideGetCharactersUseCase(characterRepository: BooksRepository): GetCharacterUseCase {
        return GetCharacterUseCase(characterRepository)
    }

    @Provides
    fun provideGetBooksUseCase(characterRepository: BooksRepository): GetBooksUseCase {
        return GetBooksUseCase(characterRepository)
    }

}
