package com.example.pet_shop.data.response

data class ResponseProducts(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)