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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOn
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ProductEntity
import com.example.ui.components.FlashSaleTimerBar
import com.example.ui.components.ProductCard
import com.example.ui.theme.AccentRed
import com.example.ui.theme.BackgroundCream
import com.example.ui.theme.PrimaryPink
import com.example.ui.theme.PrimaryYellow
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun DealsScreen(
    products: List<ProductEntity>,
    wishlistIds: Set<String>,
    onProductClick: (String) -> Unit,
    onToggleWishlist: (String) -> Unit,
    onAddToCart: (String) -> Unit
) {
    var selectedFilter by remember { mutableStateOf("ALL") }

    val filteredDeals = remember(selectedFilter, products) {
        when (selectedFilter) {
            "FLASH" -> products.filter { it.isFlashDeal }
            "UNDER_99" -> products.filter { it.isUnder99 }
            "UNDER_499" -> products.filter { it.isUnder499 }
            "TODAY" -> products.filter { it.isTodayDeal }
            else -> products.filter { it.isFlashDeal || it.isTodayDeal || it.isUnder99 || it.isUnder499 }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
    ) {
        // Flash timer bar
        FlashSaleTimerBar()

        // Filter chips bar
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val filters = listOf(
                Pair("ALL", "All Deals"),
                Pair("FLASH", "⚡ Flash Deals"),
                Pair("UNDER_99", "Under ₹99"),
                Pair("UNDER_499", "Under ₹499"),
                Pair("TODAY", "Today's Specials")
            )

            items(filters) { (key, label) ->
                val isSelected = selectedFilter == key
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSelected) PrimaryPink else Color(0xFFF3EFEA))
                        .clickable { selectedFilter = key }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .testTag("deals_chip_$key")
                ) {
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else TextPrimary
                    )
                }
            }
        }

        // Product Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredDeals) { product ->
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
