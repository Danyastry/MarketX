package com.example.pet_shop.presentation.bottomBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.pet_shop.presentation.navigation.Route.CART_SCREEN
import com.example.pet_shop.presentation.navigation.Route.FAVOURITE_SCREEN
import com.example.pet_shop.presentation.navigation.Route.MAIN_SCREEN

sealed class BottomItem(val icon: ImageVector, val route: String) {

    data object Home : BottomItem(icon = Icons.Default.Home, route = MAIN_SCREEN)
    data object Favourite : BottomItem(icon = Icons.Default.Favorite, route = FAVOURITE_SCREEN)
    data object Cart : BottomItem(icon = Icons.Default.AddShoppingCart, route = CART_SCREEN)

}