package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ProductEntity
import com.example.ui.components.ProductCard
import com.example.ui.theme.BackgroundCream
import com.example.ui.theme.PrimaryPink
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun SearchResultsScreen(
    query: String,
    products: List<ProductEntity>,
    wishlistIds: Set<String>,
    onProductClick: (String) -> Unit,
    onToggleWishlist: (String) -> Unit,
    onAddToCart: (String) -> Unit,
    onBack: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf("ALL") }
    var selectedSort by remember { mutableStateOf("POPULAR") }

    val filtered = remember(query, selectedFilter, selectedSort, products) {
        var list = if (query.isBlank()) {
            products
        } else {
            products.filter {
                it.name.contains(query, ignoreCase = true) ||
                it.brand.contains(query, ignoreCase = true) ||
                it.categoryName.contains(query, ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true)
            }
        }

        // Apply quick filter
        list = when (selectedFilter) {
            "UNDER_99" -> list.filter { it.isUnder99 || it.price <= 99.0 }
            "UNDER_499" -> list.filter { it.isUnder499 || it.price <= 499.0 }
            "DEALS" -> list.filter { it.isFlashDeal || it.isTodayDeal }
            "RATING_4" -> list.filter { it.rating >= 4.4f }
            else -> list
        }

        // Apply sort
        when (selectedSort) {
            "PRICE_LOW" -> list.sortedBy { it.price }
            "PRICE_HIGH" -> list.sortedByDescending { it.price }
            "RATING" -> list.sortedByDescending { it.rating }
            else -> list.sortedByDescending { it.reviewCount }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
    ) {
        // Search Screen Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("search_back_btn")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextPrimary
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (query.isNotBlank()) "Results for \"$query\"" else "Explore All Products",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "${filtered.size} items found",
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }
        }

        // Filter chips bar
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val filterOptions = listOf(
                Pair("ALL", "All"),
                Pair("UNDER_99", "Under ₹99"),
                Pair("UNDER_499", "Under ₹499"),
                Pair("DEALS", "⚡ Flash Deals"),
                Pair("RATING_4", "4.4★ & Above")
            )

            items(filterOptions) { (key, label) ->
                val isSelected = selectedFilter == key
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSelected) PrimaryPink else Color(0xFFF3EFEA))
                        .clickable { selectedFilter = key }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = label,
                        fontSize = 11.5.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else TextPrimary
                    )
                }
            }
        }

        // Sort Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Sort:",
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold,
                color = TextSecondary
            )
            val sorts = listOf(
                Pair("POPULAR", "Popular"),
                Pair("PRICE_LOW", "Price: Low to High"),
                Pair("PRICE_HIGH", "Price: High to Low")
            )
            sorts.forEach { (key, label) ->
                val isSelected = selectedSort == key
                Text(
                    text = label,
                    fontSize = 11.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) PrimaryPink else TextSecondary,
                    modifier = Modifier
                        .clickable { selectedSort = key }
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                )
            }
        }

        if (filtered.isEmpty()) {
            // Empty state
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.SearchOff,
                    contentDescription = null,
                    tint = TextSecondary,
                    modifier = Modifier.size(56.dp)
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "No products found for \"$query\"",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Try searching for saree, kurta, earbuds, watch or shoes",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filtered) { product ->
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
