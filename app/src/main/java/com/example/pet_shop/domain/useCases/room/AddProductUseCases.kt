package com.example.pet_shop.domain.useCases.room

import com.example.pet_shop.data.local.Dao
import com.example.pet_shop.data.response.Product
import com.example.pet_shop.domain.state.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddProductUseCases(private val dao: Dao) {
    operator fun invoke(product: Product): Flow<State<Boolean>> =
        flow {
            try {
                emit(State.Loading)
                dao.upsert(product)
                emit(State.Success(true))
            } catch (e: Exception) {
                emit(State.Error(e.message.toString()))
            }
        }
}