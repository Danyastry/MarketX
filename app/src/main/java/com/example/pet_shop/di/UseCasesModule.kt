package com.example.pet_shop.di

import com.example.pet_shop.domain.useCases.room.GetProductsUseCases
import com.example.pet_shop.domain.useCases.get.GetAllProductsUseCases
import com.example.pet_shop.domain.useCases.get.GetCategoriesUseCases
import com.example.pet_shop.domain.useCases.get.GetProductsByCategoryUseCases
import com.example.pet_shop.domain.useCases.room.AddProductUseCases
import com.example.pet_shop.domain.useCases.room.DeleteProductUseCases
import com.example.pet_shop.domain.useCases.room.GetProductUseCases
import org.koin.dsl.module

val useCases = module {

    // Room
    single { AddProductUseCases(dao = get()) }
    single { DeleteProductUseCases(dao = get()) }
    single { GetProductUseCases(dao = get()) }
    single { GetProductsUseCases(dao = get()) }

    // Get
    single { GetAllProductsUseCases(repository = get()) }
    single { GetCategoriesUseCases(repository = get()) }
    single { GetProductsByCategoryUseCases(repository = get()) }

}