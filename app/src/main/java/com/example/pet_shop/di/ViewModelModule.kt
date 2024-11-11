package com.example.pet_shop.di

import com.example.pet_shop.presentation.details.DetailsViewModel
import com.example.pet_shop.presentation.favourite.FavouriteViewModel
import com.example.pet_shop.presentation.main.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MainViewModel(
            getProductsByCategoryUseCases = get(),
            getCategoriesUseCases = get(),
            getAllProductsUseCases = get()
        )
    }
    viewModel {
        DetailsViewModel(
            addProductUseCases = get(),
            deleteProductUseCases = get(),
            getProductUseCases = get()
        )
    }
    viewModel { FavouriteViewModel(getProductsUseCases = get()) }
}