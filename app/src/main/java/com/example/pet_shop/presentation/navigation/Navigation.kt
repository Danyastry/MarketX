package com.example.pet_shop.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pet_shop.presentation.bottomBar.BottomBar
import com.example.pet_shop.presentation.cart.CartScreen
import com.example.pet_shop.presentation.details.ProductDetailsScreen
import com.example.pet_shop.presentation.favourite.FavouriteScreen
import com.example.pet_shop.presentation.main.MainScreen
import com.example.pet_shop.presentation.main.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = koinViewModel()

    val bottomBarScreens = listOf(Route.PRODUCT_DETAILS_SCREEN)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(bottomBar = {
        AnimatedVisibility(
            visible = currentRoute !in bottomBarScreens,
            enter = fadeIn(animationSpec = tween(600)),
            exit = fadeOut(animationSpec = tween(600))
        ) {
            if (currentRoute !in bottomBarScreens) {
                BottomBar(navController = navController)
            }
        }
    }) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Route.MAIN_SCREEN,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Route.MAIN_SCREEN) {
                MainScreen(navController = navController)
            }
            composable(Route.CART_SCREEN) {
                CartScreen()
            }
            composable(Route.FAVOURITE_SCREEN) {
                FavouriteScreen(navController = navController)
            }
            composable(
                Route.PRODUCT_DETAILS_SCREEN,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
                val productsId = it.arguments?.getInt("id") ?: 0
                val product = viewModel.getProductById(productsId)
                product?.let { product ->
                    ProductDetailsScreen(product = product)
                }
            }
        }
    }

}