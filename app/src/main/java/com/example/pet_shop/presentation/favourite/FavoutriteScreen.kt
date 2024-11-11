package com.example.pet_shop.presentation.favourite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pet_shop.presentation.common.ProductsList
import com.example.pet_shop.ui.theme.gravitas_reg
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavouriteScreen(
    viewModel: FavouriteViewModel = koinViewModel(),
    navController: NavController
) {

    val getProducts = viewModel.state.value.products

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .padding(top = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Favourites", fontFamily = gravitas_reg, fontSize = 30.sp, color = Color.White)
        Spacer(Modifier.height(15.dp))
        ProductsList(
            product = getProducts,
            onClick = { navController.navigate("product_details/${it.id}") })
    }
}
    
