package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.DryCleaning
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.RollerSkating
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Category
import com.example.data.model.ProductEntity
import com.example.data.sample.CatalogData
import com.example.ui.components.ProductCard
import com.example.ui.theme.BackgroundCream
import com.example.ui.theme.LightYellow
import com.example.ui.theme.PrimaryPink
import com.example.ui.theme.PrimaryYellow
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun CategoriesScreen(
    products: List<ProductEntity>,
    wishlistIds: Set<String>,
    onProductClick: (String) -> Unit,
    onToggleWishlist: (String) -> Unit,
    onAddToCart: (String) -> Unit
) {
    val categories = CatalogData.categories
    var selectedCategoryId by remember { mutableStateOf(categories.first().id) }

    val filteredProducts = remember(selectedCategoryId, products) {
        products.filter { it.categoryId == selectedCategoryId }
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
    ) {
        // Left Column: Category Vertical Selector
        LazyColumn(
            modifier = Modifier
                .width(96.dp)
                .fillMaxHeight()
                .background(Color.White)
        ) {
            items(categories) { category ->
                val isSelected = category.id == selectedCategoryId
                val icon = when (category.id) {
                    "cat_women" -> Icons.Default.Checkroom
                    "cat_men" -> Icons.Default.DryCleaning
                    "cat_electronics" -> Icons.Default.Devices
                    "cat_home" -> Icons.Default.Kitchen
                    "cat_beauty" -> Icons.Default.Spa
                    "cat_footwear" -> Icons.Default.RollerSkating
                    "cat_under99" -> Icons.Default.Savings
                    else -> Icons.Default.LocalOffer
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedCategoryId = category.id }
                        .background(
                            if (isSelected) LightYellow.copy(alpha = 0.5f) else Color.White
                        )
                        .padding(vertical = 12.dp, horizontal = 6.dp)
                        .testTag("category_tab_${category.id}"),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) PrimaryPink else Color(0xFFF3EFEA)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = category.name,
                                tint = if (isSelected) Color.White else TextPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = category.name,
                            fontSize = 10.5.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) PrimaryPink else TextPrimary,
                            textAlign = TextAlign.Center,
                            lineHeight = 13.sp
                        )
                    }
                }
            }
        }

        // Right Column: Products in selected category
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            val currentCategory = categories.find { it.id == selectedCategoryId }
            if (currentCategory != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryPink.copy(alpha = 0.1f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = currentCategory.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryPink
                            )
                            Text(
                                text = currentCategory.bannerText,
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }
                        Text(
                            text = "${filteredProducts.size} items",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(bottom = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredProducts) { product ->
                    ProductCard(
                        product = product,
                        isWishlisted = wishlistIds.contains(product.id),
                        onProductClick = { onProductClick(product.id) },
                        onToggleWishlist = { onToggleWishlist(product.id) },
                        onAddToCart = { onAddToCart(product.id) }
                    )
                }
            }
        }
    }
}
