package com.example.pet_shop.domain.useCases.get

import com.example.pet_shop.data.response.CategoryItem
import com.example.pet_shop.domain.repository.Repository
import com.example.pet_shop.domain.state.State
import kotlinx.coroutines.flow.Flow

class GetCategoriesUseCases(private val repository: Repository) {
    suspend operator fun invoke(): Flow<State<List<CategoryItem>>> {
        return repository.getCategories()
    }
}