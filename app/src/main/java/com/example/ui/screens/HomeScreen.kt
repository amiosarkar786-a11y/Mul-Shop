package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.DryCleaning
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.RollerSkating
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Category
import com.example.data.model.ProductEntity
import com.example.data.sample.CatalogData
import com.example.ui.components.FlashSaleTimerBar
import com.example.ui.components.ProductCard
import com.example.ui.theme.AccentRed
import com.example.ui.theme.BackgroundCream
import com.example.ui.theme.DealTagGradient
import com.example.ui.theme.LightPink
import com.example.ui.theme.LightYellow
import com.example.ui.theme.PrimaryPink
import com.example.ui.theme.PrimaryYellow
import com.example.ui.theme.RatingGold
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    products: List<ProductEntity>,
    wishlistIds: Set<String>,
    onProductClick: (String) -> Unit,
    onCategoryClick: (String, String) -> Unit,
    onToggleWishlist: (String) -> Unit,
    onAddToCart: (String) -> Unit,
    onViewAllDeals: () -> Unit,
    onViewOffers: () -> Unit
) {
    val flashDeals = remember(products) { products.filter { it.isFlashDeal || it.isTodayDeal } }
    val under99Items = remember(products) { products.filter { it.isUnder99 } }
    val under499Items = remember(products) { products.filter { it.isUnder499 } }
    val recommendedItems = remember(products) { products }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
            .testTag("home_screen_scroll"),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // 1. Quick Category Circles Row
        item {
            QuickCategoriesRow(
                categories = CatalogData.categories,
                onCategoryClick = onCategoryClick
            )
        }

        // 2. Hero Banners Carousel
        item {
            HeroBannerCarousel(
                onBannerClick = { onViewAllDeals() }
            )
        }

        // 3. Bank & Coupon Promo Strip
        item {
            BankOfferStrip(onOffersClick = onViewOffers)
        }

        // 4. Flash Sale Header with Live Timer
        item {
            Spacer(modifier = Modifier.height(10.dp))
            FlashSaleTimerBar()
        }

        // 5. Flash Deals Horizontal List
        item {
            DealsHorizontalSection(
                title = "Flash Deals - Up to 70% Off",
                subtitle = "Top picks selling out fast",
                products = flashDeals,
                wishlistIds = wishlistIds,
                onProductClick = onProductClick,
                onToggleWishlist = onToggleWishlist,
                onAddToCart = onAddToCart,
                onViewAllClick = onViewAllDeals
            )
        }

        // 6. Under ₹99 Budget Store
        item {
            BudgetStoreSection(
                title = "Under ₹99 Budget Store",
                badgeText = "Flat ₹99 or less",
                tagColor = PrimaryPink,
                products = under99Items,
                wishlistIds = wishlistIds,
                onProductClick = onProductClick,
                onToggleWishlist = onToggleWishlist,
                onAddToCart = onAddToCart,
                onViewAllClick = { onCategoryClick("cat_under99", "Under ₹99 Store") }
            )
        }

        // 7. Special Festive Card Banner
        item {
            FestiveSpecialBanner(onClick = onViewAllDeals)
        }

        // 8. Under ₹499 Store
        item {
            BudgetStoreSection(
                title = "Under ₹499 Pocket Store",
                badgeText = "Best Value",
                tagColor = Color(0xFFFF851B),
                products = under499Items,
                wishlistIds = wishlistIds,
                onProductClick = onProductClick,
                onToggleWishlist = onToggleWishlist,
                onAddToCart = onAddToCart,
                onViewAllClick = { onCategoryClick("cat_under499", "Under ₹499 Store") }
            )
        }

        // 9. Recommended For You (Grid)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Recommended For You (${recommendedItems.size}+ Items)",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Scroll down to explore 1000+ top Indian products",
                            fontSize = 11.5.sp,
                            color = TextSecondary
                        )
                    }
                }
            }
        }

        // 2-Column Grid items
        items(
            items = recommendedItems.chunked(2),
            key = { pair -> pair.first().id }
        ) { pair ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                for (product in pair) {
                    Box(modifier = Modifier.weight(1f)) {
                        ProductCard(
                            product = product,
                            isWishlisted = wishlistIds.contains(product.id),
                            onProductClick = { onProductClick(product.id) },
                            onToggleWishlist = { onToggleWishlist(product.id) },
                            onAddToCart = { onAddToCart(product.id) }
                        )
                    }
                }
                if (pair.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun QuickCategoriesRow(
    categories: List<Category>,
    onCategoryClick: (String, String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 10.dp),
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(categories) { category ->
            val iconVector = when (category.id) {
                "cat_women" -> Icons.Default.Checkroom
                "cat_men" -> Icons.Default.DryCleaning
                "cat_electronics" -> Icons.Default.Devices
                "cat_home" -> Icons.Default.Kitchen
                "cat_beauty" -> Icons.Default.Spa
                "cat_footwear" -> Icons.Default.RollerSkating
                "cat_under99" -> Icons.Default.Savings
                else -> Icons.Default.LocalOffer
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onCategoryClick(category.id, category.name) }
                    .padding(4.dp)
                    .testTag("category_circle_${category.id}")
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(LightYellow, LightPink.copy(alpha = 0.35f))
                            )
                        )
                        .border(1.5.dp, PrimaryYellow, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = iconVector,
                        contentDescription = category.name,
                        tint = PrimaryPink,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = category.name.split(" ").first(),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
            }
        }
    }
}

@Composable
fun HeroBannerCarousel(
    onBannerClick: () -> Unit
) {
    var activePage by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3500)
            activePage = (activePage + 1) % 3
        }
    }

    val banners = listOf(
        Pair(R.drawable.img_hero_sale, "Maha Diwali Mega Sale • Up to 80% OFF"),
        Pair(R.drawable.img_hero_fashion, "Ethnic Elegance • Banarasi & Anarkali Sets"),
        Pair(R.drawable.img_hero_sale, "Top Electronics & Daily Flash Discounts")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clickable { onBannerClick() }
                .testTag("hero_banner_card"),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = banners[activePage].first),
                    contentDescription = banners[activePage].second,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Gradient overlay at bottom
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                            )
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        text = banners[activePage].second,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Indicators
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            banners.indices.forEach { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .size(if (index == activePage) 18.dp else 6.dp, 6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(if (index == activePage) PrimaryPink else Color(0xFFD4CEC4))
                )
            }
        }
    }
}

@Composable
fun BankOfferStrip(onOffersClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 4.dp)
            .clickable { onOffersClick() },
        color = Color(0xFFFFFDE7),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryYellow)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = null,
                    tint = Color(0xFFFF851B),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Apply code MULFIRST for ₹150 OFF on 1st order",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }
            Text(
                text = "Offers >",
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPink
            )
        }
    }
}

@Composable
fun DealsHorizontalSection(
    title: String,
    subtitle: String,
    products: List<ProductEntity>,
    wishlistIds: Set<String>,
    onProductClick: (String) -> Unit,
    onToggleWishlist: (String) -> Unit,
    onAddToCart: (String) -> Unit,
    onViewAllClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = title,
                    fontSize = 16.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = subtitle,
                    fontSize = 11.5.sp,
                    color = TextSecondary
                )
            }
            Text(
                text = "View All >",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPink,
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .clickable { onViewAllClick() }
                    .padding(4.dp)
            )
        }

        // Horizontal items
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(products) { product ->
                Box(modifier = Modifier.width(165.dp)) {
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

@Composable
fun BudgetStoreSection(
    title: String,
    badgeText: String,
    tagColor: Color,
    products: List<ProductEntity>,
    wishlistIds: Set<String>,
    onProductClick: (String) -> Unit,
    onToggleWishlist: (String) -> Unit,
    onAddToCart: (String) -> Unit,
    onViewAllClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(tagColor)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = badgeText,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }
            }
            Text(
                text = "Explore >",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPink,
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .clickable { onViewAllClick() }
                    .padding(4.dp)
            )
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(products) { product ->
                Box(modifier = Modifier.width(165.dp)) {
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

@Composable
fun FestiveSpecialBanner(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(PrimaryYellow, PrimaryPink)
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Festive Indian Collection",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Text(
                        text = "Silk Sarees, Kurtas & Kitchen Appliances",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.White)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Shop Collection",
                            color = PrimaryPink,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
