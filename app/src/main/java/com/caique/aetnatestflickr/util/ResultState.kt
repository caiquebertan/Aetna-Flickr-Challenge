package com.caique.aetnatestflickr.util

sealed class ResultState<out T> {
    class Success<T>(val data: T) : ResultState<T>()
    data object Loading : ResultState<Nothing>()
    data object Error : ResultState<Nothing>()
}
