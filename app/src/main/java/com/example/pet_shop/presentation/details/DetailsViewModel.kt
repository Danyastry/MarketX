package com.example.pet_shop.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pet_shop.data.response.Product
import com.example.pet_shop.domain.state.State
import com.example.pet_shop.domain.useCases.room.AddProductUseCases
import com.example.pet_shop.domain.useCases.room.DeleteProductUseCases
import com.example.pet_shop.domain.useCases.room.GetProductUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val addProductUseCases: AddProductUseCases,
    private val deleteProductUseCases: DeleteProductUseCases,
    private val getProductUseCases: GetProductUseCases
) : ViewModel() {

    private val _state = MutableStateFlow<State<Boolean>>(State.Loading)
    val state = _state as StateFlow<State<Boolean>>

    fun addProduct(product: Product) {
        viewModelScope.launch {
            addProductUseCases.invoke(product).collect {
                _state.emit(it)
            }
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            deleteProductUseCases.invoke(product).collect {
                _state.emit(it)
            }
        }
    }

    suspend fun getProduct(id: Int): Product? {
        return getProductUseCases.invoke(id)
    }

}