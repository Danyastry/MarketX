package com.example.pet_shop.domain.useCases.room

import com.example.pet_shop.data.local.Dao
import com.example.pet_shop.data.response.Product
import kotlinx.coroutines.flow.Flow

class GetProductsUseCases(private val dao: Dao) {
    operator fun invoke(): Flow<List<Product>>{
        return dao.getProducts()
    }
}