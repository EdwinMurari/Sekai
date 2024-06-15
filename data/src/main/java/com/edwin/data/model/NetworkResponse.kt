package com.edwin.data.model

sealed class NetworkResponse<out T> {
    data class Success<out T>(val data: T) : NetworkResponse<T>()
    data class Failure(val errors: List<Error>) : NetworkResponse<Nothing>()
}