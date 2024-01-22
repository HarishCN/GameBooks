package com.example.gamesbooks.viewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.gamesbooks.data.local.entity.BooksEntity
import com.example.gamesbooks.data.local.entity.CharactersEntity
import com.example.gamesbooks.data.remote.ResponseState
import com.example.gamesbooks.domain.GetBooksUseCase
import com.example.gamesbooks.domain.GetCharacterUseCase
import com.example.gamesbooks.presentation.bookscreen.GameBookViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GameBookViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private var testDispatcher = TestCoroutineDispatcher()

    private var testCoroutineScope = TestCoroutineScope(testDispatcher)
    private lateinit var getBooksUseCase: GetBooksUseCase
    private lateinit var getCharacterUseCase: GetCharacterUseCase
    private lateinit var viewModel: GameBookViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        testDispatcher = TestCoroutineDispatcher()
        testCoroutineScope = TestCoroutineScope(testDispatcher)
        getBooksUseCase = mockk()
        getCharacterUseCase = mockk()
        viewModel = GameBookViewModel(getBooksUseCase, getCharacterUseCase)
    }

    @Test
    fun `callBookApi should update liveDataBooks`() = runBlocking {
        val mockBooks = listOf(
            BooksEntity(
                bookName = "Mocked Book 1",
                booksId = 1,
                bookUrl = "url11",
                charsUrl = arrayListOf("url1", "url2", "url3")
            ),
            BooksEntity(
                bookName = "Mocked Book 2",
                booksId = 2,
                bookUrl = "url12",
                charsUrl = arrayListOf("url4", "url5", "url6")
            )
        )
        coEvery { getBooksUseCase.invoke() } returns ResponseState.Success(mockBooks)

        // When
        viewModel.callBookApi()

        // Then
        val result = viewModel.liveDataBooks.value
        assert(result is ResponseState.Success)
        assert((result as ResponseState.Success).data == mockBooks)
    }


    @Test
    fun `callBookApi should update liveDataBooks with loading state`() =
        testCoroutineScope.runBlockingTest {
            // Given
            coEvery { getBooksUseCase.invoke() } returns ResponseState.Loading("Loading books...")

            // When
            viewModel.callBookApi()

            // Then
            val result = viewModel.liveDataBooks.value
            assertTrue(result is ResponseState.Loading)
            assertEquals("Loading books...", (result as ResponseState.Loading).loadingMessage)
        }


    // Add more test cases for other functions as needed

    @Test
    fun `callCharacterApi should update liveDataChars with success state`() =
        testCoroutineScope.runBlockingTest {
            // Given
            val mockCharacters = listOf(
                CharactersEntity(
                    charId = 1,
                    charGender = "Male",
                    titles = arrayListOf("King", "Warrior"),
                    charUrl = "https://example.com/character/1",
                    charName = "Mock Character 1",
                    alias = arrayListOf("Alias 1", "Alias 2")
                )
                // Add more Characters as needed
            )
            coEvery { getCharacterUseCase.invoke(any()) } returns ResponseState.Success(
                mockCharacters.first()
            )

            // When
            viewModel.callCharacterApi(listOf("someUrl"))

            // Then
            val result = viewModel.liveDataChars.value
            assertTrue(result is ResponseState.Success)
            assertEquals(mockCharacters, (result as ResponseState.Success).responseData)
        }

    // Cleanup
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}
