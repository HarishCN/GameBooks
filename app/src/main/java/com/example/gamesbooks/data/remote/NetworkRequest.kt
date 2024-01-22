package com.example.gamesbooks.data.remote

import retrofit2.Response


open class NetworkRequest {
    suspend fun <T> invokeApiRequest(
        apiCall: suspend () -> Response<T>
    ): ResponseState<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return ResponseState.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): ResponseState<T> =
        ResponseState.Failure(errorMessage)
}