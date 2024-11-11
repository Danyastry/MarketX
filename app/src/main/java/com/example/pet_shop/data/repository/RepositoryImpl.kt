package com.example.pet_shop.data.repository

import com.example.pet_shop.data.api.Api
import com.example.pet_shop.data.response.CategoryItem
import com.example.pet_shop.data.response.Product
import com.example.pet_shop.domain.state.State
import com.example.pet_shop.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class RepositoryImpl(private val api: Api) : Repository {

    override suspend fun getAllProducts(): Flow<State<List<Product>>> =
        safeCall { api.getAllProducts().products }

    override suspend fun getProductsByCategory(category: String): Flow<State<List<Product>>> =
        safeCall { api.getProductsByCategory(category).products }

    override suspend fun getCategories(): Flow<State<List<CategoryItem>>> =
        safeCall { api.getCategories() }

    private fun <T> safeCall(apiCall: suspend () -> T): Flow<State<T>> = flow {
        emit(State.Loading)
        try {
            emit(State.Success(apiCall()))
        } catch (e: HttpException) {
            emit(State.Error("Server Error: ${e.message}"))
        } catch (e: IOException) {
            emit(State.Error("Network Error: ${e.message}"))
        } catch (e: Exception) {
            emit(State.Error("Unexpected Error: ${e.message}"))
        }
    }

}