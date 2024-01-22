package com.example.gamesbooks.data.repository

import com.example.gamesbooks.data.model.toBook
import com.example.gamesbooks.data.model.toCharacter
import com.example.gamesbooks.data.local.LocalDataSource
import com.example.gamesbooks.data.local.entity.BooksEntity
import com.example.gamesbooks.data.local.entity.CharactersEntity
import com.example.gamesbooks.data.remote.ResponseState
import com.example.gamesbooks.data.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class BooksRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
): BooksRepository {

    override suspend fun getBooks(): ResponseState<List<BooksEntity>> {
        return withContext(Dispatchers.IO) {
            val serverResponse = remoteDataSource.getBooks().data
            if (!serverResponse.isNullOrEmpty()) {
                ResponseState.Success(serverResponse.map { it.toBook() }.toList())
            } else ResponseState.Failure("Unknown exception. Try again later")
        }
    }

    override suspend fun getCharacter(charUrl: String): ResponseState<CharactersEntity> {
        return withContext(Dispatchers.IO) {
            val response: CharactersEntity
            if (localDataSource.isUrlAdded(charUrl)) {
                response = localDataSource.getCharacter(charUrl)
            } else {
                response = remoteDataSource.getCharacterDetails(charUrl).data?.toCharacter()!!
                localDataSource.insertCharacter(response)
            }
            ResponseState.Success(response)
        }
    }


}
