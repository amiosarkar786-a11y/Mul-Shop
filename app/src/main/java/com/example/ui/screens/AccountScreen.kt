package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.data.model.UserProfileEntity
import com.example.ui.theme.AccentRed
import com.example.ui.theme.PrimaryPink
import com.example.ui.theme.PrimaryYellow
import com.example.ui.theme.RatingGold
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun AccountScreen(
    userProfile: UserProfileEntity?,
    isLoggedIn: Boolean,
    ordersCount: Int,
    wishlistCount: Int,
    onLoginClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onNavigateToOrders: () -> Unit,
    onNavigateToWishlist: () -> Unit,
    onNavigateToAddresses: () -> Unit,
    onNavigateToCoupons: () -> Unit,
    onNavigateToProfileDetail: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToHelpSupport: () -> Unit,
    onNavigateToNotifications: () -> Unit
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    val userName = if (isLoggedIn) (userProfile?.name?.ifBlank { "User" } ?: "User") else "User"

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = {
                Text(
                    text = "Log out",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = TextPrimary
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to log out of Mul Shop?",
                    fontSize = 13.5.sp,
                    color = TextSecondary,
                    lineHeight = 18.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        onLogoutClick()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AccentRed),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("confirm_logout_btn")
                ) {
                    Text("Log out", fontWeight = FontWeight.Bold, color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel", color = TextSecondary, fontWeight = FontWeight.SemiBold)
                }
            },
            shape = RoundedCornerShape(14.dp),
            containerColor = Color.White
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding(),
        contentPadding = PaddingValues(bottom = 36.dp)
    ) {
        // 1. Top Header: "Hey, User" + "[ 🎧 Help ]" pill
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Hey, $userName",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                // Help Button Pill
                Surface(
                    onClick = onNavigateToHelpSupport,
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, Color(0xFFD6D6D6)),
                    color = Color.White,
                    modifier = Modifier.testTag("account_top_help_btn")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.HeadsetMic,
                            contentDescription = "Help",
                            tint = TextPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Help",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                    }
                }
            }
        }

        // 2. SuperCoin Balance Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F8FD)),
                border = BorderStroke(1.dp, Color(0xFFE8EBFA))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Your SuperCoin balance",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )

                        // SuperCoin pill
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, Color(0xFFE0E0E0))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFFFB300)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("⚡", fontSize = 9.sp)
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isLoggedIn) "150" else "0",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            }
                        }
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 10.dp),
                        color = Color(0xFFE8EBFA)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToCoupons() },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Extra SuperCoins and Rewards with ",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                            Text(
                                text = "✨ Plus Silver",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF5B69C4)
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = TextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        // 3. Two Big Action Buttons: [ 📦 Orders ] and [ 🤍 Wishlist ]
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Orders Button
                Surface(
                    onClick = onNavigateToOrders,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .testTag("account_orders_btn"),
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Color(0xFFD6D6D6))
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Inventory2,
                            contentDescription = "Orders",
                            tint = Color(0xFF2874F0),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Orders",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                }

                // Wishlist Button
                Surface(
                    onClick = onNavigateToWishlist,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .testTag("account_wishlist_btn"),
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Color(0xFFD6D6D6))
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Wishlist",
                            tint = Color(0xFF2874F0),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Wishlist",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                }
            }
        }

        // 4. Section: Finance Options
        item {
            SectionHeader(title = "Finance Options")

            FinanceRowItem(
                icon = Icons.Default.Savings,
                iconBg = Color(0xFFE8F0FE),
                iconTint = Color(0xFF2874F0),
                title = "Mul Personal Loan",
                subtitle = "Up to ₹10 Lakh | Instant disbursal",
                onClick = onNavigateToCoupons
            )
            DividerLine()

            FinanceRowItem(
                icon = Icons.Default.CreditCard,
                iconBg = Color(0xFFE3F2FD),
                iconTint = Color(0xFF1976D2),
                title = "Pre-Approved Supermoney Credit Card",
                subtitle = "1% cashback on UPI & Non-UPI | 100% Approval",
                onClick = onNavigateToCoupons
            )
            DividerLine()

            FinanceRowItem(
                icon = Icons.Default.CalendarMonth,
                iconBg = Color(0xFFEDE7F6),
                iconTint = Color(0xFF673AB7),
                title = "Mul Pay Later / EMI - Special!",
                subtitle = "Up to ₹2 Lakh Credit | ₹1,000 Discount*",
                onClick = onNavigateToCoupons
            )
            DividerLine()
        }

        // 5. Section: Recently Viewed Stores (Horizontal carousels as shown in screenshot)
        item {
            SectionHeader(title = "Recently Viewed Stores")

            val recentStores = listOf(
                Pair("Mobiles", "https://images.unsplash.com/photo-1598327105666-5b89351aff97?w=300&auto=format&fit=crop&q=80"),
                Pair("True Wireless", "https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=300&auto=format&fit=crop&q=80"),
                Pair("Men's Sports", "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=300&auto=format&fit=crop&q=80"),
                Pair("Microphones", "https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=300&auto=format&fit=crop&q=80")
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(recentStores) { (name, img) ->
                    Column(
                        modifier = Modifier.width(84.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            modifier = Modifier.size(80.dp),
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFF7F7F7),
                            border = BorderStroke(1.dp, Color(0xFFEBEBEB))
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(img)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = name,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = name,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            DividerLine()
        }

        // 6. Section: Coupons & Offers
        item {
            SectionHeader(title = "Coupons & Offers")

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clickable { onNavigateToCoupons() },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F7FD)),
                border = BorderStroke(1.dp, Color(0xFFECE6F8))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF7E57C2)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalOffer,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Explore and grab the best deals",
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            DividerLine()
        }

        // 7. Section: Sponsored (SBI Card / Banking Banner)
        item {
            SectionHeader(title = "Sponsored")

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clickable { onNavigateToCoupons() },
                shape = RoundedCornerShape(14.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFFF9EDEE), Color(0xFFFFFFFF), Color(0xFFEEF3FC))
                            )
                        )
                        .padding(14.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "SBI Card",
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp,
                                color = Color(0xFF0072BC)
                            )
                            Box(
                                modifier = Modifier
                                    .border(1.dp, Color(0xFFCCCCCC), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            ) {
                                Text("AD", fontSize = 9.sp, color = TextSecondary, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "10X",
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF8B263E),
                                    lineHeight = 30.sp
                                )
                                Text(
                                    text = "REWARD POINTS",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF8B263E)
                                )
                                Text(
                                    text = "on online spends with Exclusive Partners",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Apply now",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = TextPrimary
                                )
                            }

                            // Credit Card Graphic representation
                            Box(
                                modifier = Modifier
                                    .size(width = 80.dp, height = 50.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(
                                        Brush.linearGradient(
                                            listOf(Color(0xFF8B263E), Color(0xFFB54B64))
                                        )
                                    )
                                    .padding(6.dp)
                            ) {
                                Text("SimplyCLICK", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
            DividerLine()
        }

        // 8. Section: Profile Settings
        item {
            SectionHeader(title = "Profile Settings")

            AccountActionRow(
                icon = Icons.Default.PersonOutline,
                title = "Edit profile",
                onClick = onNavigateToProfileDetail
            )
            AccountActionRow(
                icon = Icons.Default.LocationOn,
                title = "Saved addresses",
                onClick = onNavigateToAddresses
            )
            AccountActionRow(
                icon = Icons.Default.Translate,
                title = "Change language",
                badgeText = "English",
                onClick = onNavigateToSettings
            )
            AccountActionRow(
                icon = Icons.Default.NotificationsNone,
                title = "Notification settings",
                onClick = onNavigateToNotifications
            )
            AccountActionRow(
                icon = Icons.Default.PlayCircleOutline,
                title = "My subscriptions",
                onClick = onNavigateToCoupons
            )
            DividerLine()
        }

        // 9. Section: Payments & Wallets
        item {
            SectionHeader(title = "Payments & Wallets")

            AccountActionRow(
                icon = Icons.Default.CardGiftcard,
                title = "Gift card",
                badgeButtonText = "Add gift card",
                onClick = onNavigateToCoupons
            )
            AccountActionRow(
                icon = Icons.Default.AccountBalanceWallet,
                title = "Saved payment methods",
                onClick = onNavigateToCoupons
            )
            DividerLine()
        }

        // 10. Section: Privacy & Security
        item {
            SectionHeader(title = "Privacy & Security")

            AccountActionRow(
                icon = Icons.Default.Lock,
                title = "Privacy center",
                onClick = onNavigateToSettings
            )
            AccountActionRow(
                icon = Icons.Default.Smartphone,
                title = "Manage devices",
                badgeText = "1 active device",
                onClick = onNavigateToSettings
            )
            DividerLine()
        }

        // 11. Section: Earn with Mul Shop
        item {
            SectionHeader(title = "Earn with Mul Shop")

            AccountActionRow(
                icon = Icons.Default.PlayCircleOutline,
                title = "Mul Shop affiliate program",
                onClick = onNavigateToCoupons
            )
            AccountActionRow(
                icon = Icons.Default.Storefront,
                title = "Sell on Mul Shop",
                onClick = onNavigateToHelpSupport
            )
            DividerLine()
        }

        // 12. Section: FAQ & Terms
        item {
            SectionHeader(title = "FAQ & Terms")

            AccountActionRow(
                icon = Icons.Default.HelpOutline,
                title = "FAQs",
                onClick = onNavigateToHelpSupport
            )
            AccountActionRow(
                icon = Icons.Default.Description,
                title = "Terms, Policies & Licences",
                onClick = onNavigateToSettings
            )
            DividerLine()
        }

        // 13. Section: My Activity
        item {
            SectionHeader(title = "My Activity")

            AccountActionRow(
                icon = Icons.Default.ChatBubbleOutline,
                title = "Questions & Answers",
                onClick = onNavigateToHelpSupport
            )
            DividerLine()
        }

        // 14. Section: Follow Us On
        item {
            SectionHeader(title = "Follow Us On")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SocialPill(
                    iconText = "📷",
                    label = "Instagram",
                    modifier = Modifier.weight(1f)
                )
                SocialPill(
                    iconText = "▶️",
                    label = "YouTube",
                    modifier = Modifier.weight(1f)
                )
                SocialPill(
                    iconText = "💼",
                    label = "LinkedIn",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))
        }

        // 15. Log out / Log in Button (matching Screenshot 4)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                if (isLoggedIn) {
                    OutlinedButton(
                        onClick = { showLogoutDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("account_logout_btn"),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color(0xFF2874F0)),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF2874F0))
                    ) {
                        Text(
                            text = "Log out",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2874F0)
                        )
                    }
                } else {
                    Button(
                        onClick = onLoginClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("account_bottom_login_btn"),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2874F0))
                    ) {
                        Text(
                            text = "Log In / Sign Up",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // App Version
            Text(
                text = "v3240500",
                fontSize = 12.sp,
                color = Color(0xFF757575),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Watermark: "Trusted by 50 Cr+ Indians" (large faded grey typography)
            Text(
                text = "Trusted by\n50 Cr+ Indians",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFFEAEAEA),
                lineHeight = 36.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
    )
}

@Composable
private fun DividerLine() {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp),
        color = Color(0xFFF1F1F1)
    )
}

@Composable
private fun FinanceRowItem(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = TextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun AccountActionRow(
    icon: ImageVector,
    title: String,
    badgeText: String? = null,
    badgeButtonText: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF2F4F7)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF2874F0),
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            if (badgeText != null) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFFF0F5FF)
                ) {
                    Text(
                        text = badgeText,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF2874F0),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
            }

            if (badgeButtonText != null) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFFF0F5FF)
                ) {
                    Text(
                        text = badgeButtonText,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2874F0),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = TextSecondary,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun SocialPill(
    iconText: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.height(44.dp),
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFF7F7F7),
        border = BorderStroke(1.dp, Color(0xFFEBEBEB))
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(iconText, fontSize = 14.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
        }
    }
}
