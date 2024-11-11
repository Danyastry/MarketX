package com.example.pet_shop.presentation.details

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pet_shop.data.response.Product
import com.example.pet_shop.presentation.details.common.ReviewItem
import com.example.pet_shop.ui.theme.montserrat_bold
import es.dmoral.toasty.Toasty
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductDetailsScreen(viewModel: DetailsViewModel = koinViewModel(), product: Product) {

    val scrollState = rememberScrollState()

    val context = LocalContext.current
    val isFavourite = remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }

    val imageSize: Dp by animateDpAsState(
        targetValue = if (isExpanded) 400.dp else 250.dp,
        animationSpec = tween(durationMillis = 800)
    )
    val scale: Float by animateFloatAsState(
        targetValue = if (isExpanded) 1.8f else 1f, animationSpec = tween(durationMillis = 800)
    )
    val scaleFavourite by animateFloatAsState(
        targetValue = if (isFavourite.value) 1.8f else 1.5f,
        animationSpec = tween(durationMillis = 300)
    )
    val tint by animateColorAsState(
        targetValue = if (isFavourite.value) Color.White else Color.Gray,
        animationSpec = tween(durationMillis = 300)
    )


    LaunchedEffect(isFavourite.value) {
        isFavourite.value = viewModel.getProduct(product.id) != null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        AsyncImage(
            model = product.images.firstOrNull(),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .fillMaxWidth()
                .height(imageSize)
                .clip(RoundedCornerShape(12.dp))
                .scale(scale)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = { isExpanded = !isExpanded })
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = product.title,
            fontFamily = montserrat_bold,
            color = Color.White
        )

        Text(
            text = if (!product.brand.isNullOrEmpty()) "${product.category} • ${product.brand}" else product.category,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "$${product.price}",
                fontFamily = montserrat_bold,
                color = Color.White
            )
            if (product.discountPercentage > 0) {
                Text(
                    text = "${product.discountPercentage}% OFF",
                    fontFamily = montserrat_bold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = product.description,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Availability: ${product.availabilityStatus} (${product.stock} in stock)",
            color = if (product.stock > 0) Color.Green else Color.Red
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Shipping: ${product.shippingInformation}",
            color = Color.White
        )

        Text(
            text = "Warranty: ${product.warrantyInformation}",
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Customer Reviews",
            fontFamily = montserrat_bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        product.reviews.forEach { review ->
            ReviewItem(review)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { },
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Buy Now",
                    color = Color.White,
                    fontFamily = montserrat_bold,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        if (isFavourite.value) {
                            viewModel.deleteProduct(product)
                            isFavourite.value = false
                            Toasty
                                .success(
                                    context,
                                    "Removed from favourites",
                                    Toasty.LENGTH_SHORT,
                                    true
                                )
                                .show()
                        } else {
                            viewModel.addProduct(product)
                            isFavourite.value = true
                            Toasty
                                .success(
                                    context,
                                    "Added to favourites",
                                    Toasty.LENGTH_SHORT,
                                    true
                                )
                                .show()
                        }
                    }
                    .background(Color.DarkGray)
                    .padding(horizontal = 5.dp)
                    .height(55.dp)
                    .width(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    tint = tint,
                    contentDescription = null,
                    modifier = Modifier.scale(scaleFavourite)
                )
            }

        }
    }
}

