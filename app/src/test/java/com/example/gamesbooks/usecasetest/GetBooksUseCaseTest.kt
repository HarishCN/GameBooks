package com.example.gamesbooks.usecasetest

import org.junit.Test

import org.junit.Assert.*

import com.example.gamesbooks.data.local.entity.BooksEntity
import com.example.gamesbooks.data.remote.ResponseState
import com.example.gamesbooks.data.repository.BooksRepository
import com.example.gamesbooks.domain.GetBooksUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetBooksUseCaseTest {

    @Mock
    private lateinit var mockRepository: BooksRepository

    private lateinit var getBooksUseCase: GetBooksUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getBooksUseCase = GetBooksUseCase(mockRepository)
    }

    @Test
    fun `invoke should return success response when repository returns data`() = runBlocking {
        // Create mock book data
        val mockBooksList = listOf(
            BooksEntity(1, arrayListOf("url1", "url2", "url3"), "Mocked Book 1", "url11"),
            BooksEntity(2, arrayListOf("url4", "url5", "url6"), "Mocked Book 2", "url12")
        )

        // Mock
        `when`(mockRepository.getBooks()).thenReturn(ResponseState.Success(mockBooksList))

        // Invoke the use case
        val result = getBooksUseCase.invoke()

        // Assert the result
        assertTrue(result is ResponseState.Success)
        assertEquals(mockBooksList, (result as ResponseState.Success).data)
    }

    @Test
    fun `invoke should return error response when repository returns an error`() = runBlocking {
        // Arrange
        val errorMessage = "Error fetching books"
        `when`(mockRepository.getBooks()).thenReturn(ResponseState.Failure(errorMessage))

        // Act
        val result = getBooksUseCase.invoke()

        // Assert
        assertEquals(errorMessage, (result as? ResponseState.Failure)?.errorMessage)
    }


}
