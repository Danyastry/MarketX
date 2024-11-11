package com.example.pet_shop.domain.useCases.room

import com.example.pet_shop.data.local.Dao
import com.example.pet_shop.data.response.Product

class GetProductUseCases(private val dao: Dao) {
    suspend operator fun invoke(id: Int): Product? {
        return dao.getProduct(id)
    }
}