package com.example.pet_shop.domain.state

sealed class State<out T> {
    data class Success<T>(val data: T): State<T>()
    data class Error(val message: String): State<Nothing>()
    data object Loading: State<Nothing>()
}