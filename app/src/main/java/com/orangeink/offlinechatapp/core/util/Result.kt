package com.orangeink.offlinechatapp.core.util

/**
 * A generic class that holds a value with its loading status.
 */
sealed class Result<out T : Any?> {

    data object Loading : Result<Nothing>()

    data class Success<out T : Any>(val data: T) : Result<T>()

    data class Error(val message: String? = null) : Result<Nothing>()
}