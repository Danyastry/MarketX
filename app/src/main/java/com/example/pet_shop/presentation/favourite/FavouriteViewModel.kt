package com.example.pet_shop.presentation.favourite

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pet_shop.domain.useCases.room.GetProductsUseCases
import kotlinx.coroutines.launch

class FavouriteViewModel(private val getProductsUseCases: GetProductsUseCases) : ViewModel() {
    private val _state = mutableStateOf(FavouriteState())
    val state: State<FavouriteState> = _state

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            getProductsUseCases.invoke().collect { products ->
                _state.value = FavouriteState(products.asReversed())
            }
        }
    }
}