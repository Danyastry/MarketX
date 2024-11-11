package com.example.pet_shop.data.api

import com.example.pet_shop.data.response.CategoryItem
import com.example.pet_shop.data.response.ResponseProducts
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://dummyjson.com"

interface Api {

    @GET("/products")
    suspend fun getAllProducts(): ResponseProducts

    @GET("/products/categories")
    suspend fun getCategories(): List<CategoryItem>

    @GET("/products/category/{category}")
    suspend fun getProductsByCategory(
        @Path("category") category: String
    ): ResponseProducts
}