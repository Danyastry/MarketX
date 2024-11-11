package com.example.pet_shop.domain.repository

import com.example.pet_shop.data.response.CategoryItem
import com.example.pet_shop.data.response.Product
import com.example.pet_shop.domain.state.State
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getAllProducts(): Flow<State<List<Product>>>
    suspend fun getProductsByCategory(category: String): Flow<State<List<Product>>>
    suspend fun getCategories(): Flow<State<List<CategoryItem>>>
}