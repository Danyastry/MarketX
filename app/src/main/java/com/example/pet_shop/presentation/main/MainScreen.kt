package com.example.pet_shop.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pet_shop.domain.state.State
import com.example.pet_shop.presentation.common.EmptyScreen
import com.example.pet_shop.presentation.common.ProductCard
import com.example.pet_shop.presentation.common.ShimmerEffect
import com.example.pet_shop.presentation.main.slider.Slider
import com.example.pet_shop.ui.theme.LightBlue
import com.example.pet_shop.ui.theme.gravitas_reg
import com.example.pet_shop.ui.theme.montserrat_bold
import com.example.pet_shop.ui.theme.montserrat_reg
import org.koin.androidx.compose.koinViewModel


@Composable
fun MainScreen(
    viewModel: MainViewModel = koinViewModel(), navController: NavController,
) {

    val productsState by viewModel.productsState.collectAsState()
    val categoriesState by viewModel.categoriesState.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (categoriesState) {
            is State.Success -> {
                Spacer(Modifier.height(22.dp))
                Text(
                    "- Market X -",
                    fontFamily = gravitas_reg,
                    fontSize = 32.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(20.dp))
                Slider()
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Categories",
                    fontSize = 18.sp,
                    fontFamily = montserrat_bold,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 17.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))

                val categories = (categoriesState as? State.Success)?.data.orEmpty()
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(1),
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    items(categories) { category ->
                        val isSelected = selectedCategory == category.slug
                        Button(
                            onClick = { viewModel.getProductsByCategory(category.slug) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = LightBlue
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = category.name,
                                fontSize = 15.sp,
                                fontFamily = if (isSelected) montserrat_bold else montserrat_reg,
                                color = Color.Black
                            )
                        }
                    }
                }
            }

            is State.Error -> {
                EmptyScreen(categoriesState as State.Error)
            }

            State.Loading -> {
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    item(10) {
                        repeat(10) {
                            ShimmerEffect(
                                modifier = Modifier
                                    .sizeIn(maxWidth = 70.dp, maxHeight = 50.dp)
                                    .padding(horizontal = 2.dp, vertical = 5.dp)
                            )
                        }
                    }
                }
            }
        }

        when (productsState) {
            is State.Success -> {
                val data = productsState as State.Success
                Text(
                    text = "Products",
                    fontSize = 18.sp,
                    fontFamily = montserrat_bold,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 17.dp)
                        .padding(top = 12.dp)
                )
                Spacer(Modifier.height(8.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(data.data) { product ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            ProductCard(product) {
                                navController.navigate("product_details/${product.id}")
                            }
                        }
                    }
                }
            }

            is State.Error -> {
                EmptyScreen(productsState as State.Error)
            }

            State.Loading -> {
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    items(10) {
                        repeat(10) {
                            ShimmerEffect(
                                modifier = Modifier
                                    .height(360.dp)
                                    .width(300.dp)
                                    .padding(10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
