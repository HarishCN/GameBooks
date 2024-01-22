package com.example.gamesbooks.data.remote

sealed class ResponseState<T>(val data: T? = null, val message: String? = null) {

    data class Success<T>(val responseData: T) : ResponseState<T>(responseData)

    data class Failure<T>(val errorMessage: String?) : ResponseState<T>(message = errorMessage)

    data class Loading<T>(val loadingMessage: String? = null) : ResponseState<T>(message = loadingMessage)
}