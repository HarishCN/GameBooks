package com.example.gamesbooks.presentation.bookscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamesbooks.data.local.entity.BooksEntity
import com.example.gamesbooks.data.local.entity.CharactersEntity
import com.example.gamesbooks.data.remote.ResponseState
import com.example.gamesbooks.domain.GetBooksUseCase
import com.example.gamesbooks.domain.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameBookViewModel @Inject constructor(private val getBooksUseCase: GetBooksUseCase,
                                            private val getCharacterUseCase: GetCharacterUseCase
) : ViewModel() {

    private val _liveDataBooks = MutableLiveData<ResponseState<List<BooksEntity>>>()
    val liveDataBooks: LiveData<ResponseState<List<BooksEntity>>> get() = _liveDataBooks

    private val _liveDataChars = MutableLiveData<ResponseState<List<CharactersEntity>>>()
    val liveDataChars: LiveData<ResponseState<List<CharactersEntity>>> get() = _liveDataChars

    fun callBookApi() {
        viewModelScope.launch {
            _liveDataBooks.postValue(ResponseState.Loading())
            val result = getBooksUseCase.invoke()
            _liveDataBooks.postValue(result)
        }
    }

    suspend fun callCharacterApi(charsUrl: List<String>) {
        _liveDataChars.postValue(ResponseState.Loading())
        val tempCharList = ArrayList<CharactersEntity>()
        viewModelScope.async {
            charsUrl.forEach {
                val response = getCharacterUseCase.invoke(it)
                if (response.data != null)
                    tempCharList.add(response.data)
            }
        }.await()
        _liveDataChars.postValue(ResponseState.Success(tempCharList))
    }
}