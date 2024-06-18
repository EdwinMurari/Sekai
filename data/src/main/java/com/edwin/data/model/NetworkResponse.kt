package com.edwin.data.model

sealed class NetworkResponse<out T> {
    data class Success<out T>(val data: T) : NetworkResponse<T>()
    data class Error(val errors: List<java.lang.Error>) : NetworkResponse<Nothing>()
}