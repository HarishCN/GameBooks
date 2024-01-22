import com.example.gamesbooks.data.local.entity.CharactersEntity
import com.example.gamesbooks.data.remote.ResponseState
import com.example.gamesbooks.data.repository.BooksRepository
import com.example.gamesbooks.domain.GetCharacterUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetCharacterUseCaseTest {

    @Mock
    private lateinit var mockRepository: BooksRepository

    private lateinit var getCharacterUseCase: GetCharacterUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getCharacterUseCase = GetCharacterUseCase(mockRepository)
    }

    @Test
    fun `invoke should return character data when repository returns success`() = runBlocking {
        // Arrange
        val mockCharacterUrl = "mocked_character_url"
        val mockCharacterData = CharactersEntity(
            charId = 1,
            charGender = "Male",
            titles = arrayListOf("King", "Warrior"),
            charUrl = "https://example.com/character/1",
            charName = "Mock Character 1",
            alias = arrayListOf("Alias 1", "Alias 2")
        )
        `when`(mockRepository.getCharacter(mockCharacterUrl)).thenReturn(
            ResponseState.Success(
                mockCharacterData
            )
        )

        // Act
        val result = getCharacterUseCase.invoke(mockCharacterUrl)

        // Assert
        assertEquals(ResponseState.Success(mockCharacterData), result)
    }

    @Test
    fun `invoke should return error response when repository returns an error`() = runBlocking {
        // Arrange
        val mockCharacterUrl = "mocked_character_url"
        val errorMessage = "Error fetching character data"
        `when`(mockRepository.getCharacter(mockCharacterUrl)).thenReturn(
            ResponseState.Failure(
                errorMessage
            )
        )

        // Act
        val result = getCharacterUseCase.invoke(mockCharacterUrl)

        // Assert
        assertEquals(errorMessage, (result as? ResponseState.Failure)?.errorMessage)
    }
}
