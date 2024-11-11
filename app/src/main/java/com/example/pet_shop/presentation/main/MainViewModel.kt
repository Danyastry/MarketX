package com.example.pet_shop.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pet_shop.data.response.CategoryItem
import com.example.pet_shop.data.response.Product
import com.example.pet_shop.domain.state.State
import com.example.pet_shop.domain.useCases.get.GetAllProductsUseCases
import com.example.pet_shop.domain.useCases.get.GetCategoriesUseCases
import com.example.pet_shop.domain.useCases.get.GetProductsByCategoryUseCases
import com.example.pet_shop.presentation.common.EmptyScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getProductsByCategoryUseCases: GetProductsByCategoryUseCases,
    private val getAllProductsUseCases: GetAllProductsUseCases,
    private val getCategoriesUseCases: GetCategoriesUseCases
) : ViewModel() {


    private val _productsState = MutableStateFlow<State<List<Product>>>(State.Loading)
    val productsState = _productsState.asStateFlow()

    private val _categoriesState = MutableStateFlow<State<List<CategoryItem>>>(State.Loading)
    val categoriesState = _categoriesState.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory = _selectedCategory.asStateFlow()

    init {
        getAllProducts()
        getCategories()
    }

    private fun getAllProducts() {
        try {
            viewModelScope.launch {
                _productsState.value = State.Loading
                getAllProductsUseCases().collect { state ->
                    _productsState.value = state
                }
            }
        } catch (e: Exception) {
            _productsState.value = State.Error(e.message.toString())
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            try {
                _categoriesState.value = State.Loading
                getCategoriesUseCases().collect { state ->
                    _categoriesState.value = state
                }
            } catch (e: Exception) {
                _categoriesState.value = State.Error(e.message.toString())
            }
        }
    }

    fun getProductsByCategory(category: String) {
        if (category == _selectedCategory.value) {
            _selectedCategory.value = null
            getAllProducts()
        } else {
            _selectedCategory.value = category
            viewModelScope.launch {
                try {
                    _productsState.value = State.Loading
                    getProductsByCategoryUseCases(category).collect { state ->
                        _productsState.value = state
                    }
                } catch (e: Exception) {
                    _productsState.value = State.Error(e.message.toString())
                }
            }
        }
    }

    fun getProductById(id: Int): Product? {
        return when(val state = _productsState.value) {
            is State.Success -> {
                state.data.find { it.id == id }
            }
            else -> null
        }
    }

}