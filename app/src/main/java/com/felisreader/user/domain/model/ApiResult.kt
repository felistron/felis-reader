package com.felisreader.user.domain.model

sealed class ApiResult<out T> {
    data class Success<out T>(val body: T): ApiResult<T>()
    data class Failure(val code: Int): ApiResult<Nothing>()
}