package com.example.ui

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.MulShopBottomBar
import com.example.ui.components.MulShopHeader
import com.example.ui.navigation.BottomTab
import com.example.ui.navigation.Screen
import com.example.ui.screens.AccountScreen
import com.example.ui.screens.CartScreen
import com.example.ui.screens.CategoriesScreen
import com.example.ui.screens.CategoryListingScreen
import com.example.ui.screens.CustomerReviewScreen
import com.example.ui.screens.DealsScreen
import com.example.ui.screens.DeliveryAddressScreen
import com.example.ui.screens.HelpSupportScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.MyOrdersScreen
import com.example.ui.screens.NotificationsScreen
import com.example.ui.screens.OffersScreen
import com.example.ui.screens.OnboardingScreen
import com.example.ui.screens.OrderPlacedScreen
import com.example.ui.screens.OrderSummaryScreen
import com.example.ui.screens.PaymentScreen
import com.example.ui.screens.ProductDetailsScreen
import com.example.ui.screens.ProfileDetailScreen
import com.example.ui.screens.SearchResultsScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.TrackOrderScreen
import com.example.ui.screens.WishlistScreen
import com.example.ui.theme.BackgroundCream
import com.example.ui.theme.MulShopTheme
import com.example.ui.viewmodel.MulShopViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MulShopApp(
    viewModel: MulShopViewModel = viewModel()
) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val currentTab by viewModel.currentBottomTab.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()

    val allProducts by viewModel.allProducts.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()
    val wishlistProducts by viewModel.wishlistProducts.collectAsState()
    val wishlistIds = remember(wishlistProducts) { wishlistProducts.map { it.id }.toSet() }
    val addresses by viewModel.addresses.collectAsState()
    val defaultAddress by viewModel.defaultAddress.collectAsState()
    val orders by viewModel.orders.collectAsState()
    val notifications by viewModel.notifications.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()

    val cartSubtotal by viewModel.cartSubtotal.collectAsState()
    val cartMrpTotal by viewModel.cartMrpTotal.collectAsState()
    val couponDiscount by viewModel.couponDiscount.collectAsState()
    val cartFinalTotal by viewModel.cartFinalTotal.collectAsState()
    val appliedCoupon by viewModel.appliedCoupon.collectAsState()

    val searchQuery by viewModel.searchQuery.collectAsState()
    val deliveryLocation by viewModel.deliveryLocation.collectAsState()
    val selectedPaymentMethod by viewModel.selectedPaymentMethod.collectAsState()
    val isHindi by viewModel.isLanguageHindi.collectAsState()

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.toastEvent.collectLatest { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    // Hardware and gesture BackHandler
    BackHandler(enabled = currentScreen !is Screen.Home && currentScreen !is Screen.Splash) {
        if (!viewModel.goBack()) {
            viewModel.switchBottomTab(BottomTab.HOME)
        }
    }

    val isMainTab = currentScreen is Screen.Home ||
            currentScreen is Screen.Categories ||
            currentScreen is Screen.Deals ||
            currentScreen is Screen.Cart ||
            currentScreen is Screen.Account

    MulShopTheme(darkTheme = isDarkMode) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                if (isMainTab && currentScreen !is Screen.Account) {
                    MulShopHeader(
                        searchQuery = searchQuery,
                        onSearchQueryChange = {
                            viewModel.setSearchQuery(it)
                            if (currentScreen !is Screen.SearchResults) {
                                viewModel.navigateTo(Screen.SearchResults(it))
                            }
                        },
                        onSearchTriggered = {
                            viewModel.navigateTo(Screen.SearchResults(searchQuery))
                        },
                        deliveryLocation = deliveryLocation,
                        onLocationClick = {
                            viewModel.navigateTo(Screen.DeliveryAddress(isForCheckout = false))
                        },
                        wishlistCount = wishlistProducts.size,
                        cartCount = cartItems.sumOf { it.cartItem.quantity },
                        notificationCount = notifications.count { !it.isRead },
                        onWishlistClick = {
                            viewModel.navigateTo(Screen.Wishlist(isFromHome = true))
                        },
                        onCartClick = {
                            viewModel.switchBottomTab(BottomTab.CART)
                        },
                        onNotificationClick = {
                            viewModel.navigateTo(Screen.Notifications)
                        },
                        onProfileClick = {
                            viewModel.switchBottomTab(BottomTab.ACCOUNT)
                        }
                    )
                }
            },
            bottomBar = {
                if (isMainTab) {
                    MulShopBottomBar(
                        currentTab = currentTab,
                        onTabSelected = { viewModel.switchBottomTab(it) },
                        cartCount = cartItems.sumOf { it.cartItem.quantity }
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundCream)
                    .padding(innerPadding)
            ) {
                when (val screen = currentScreen) {
                    is Screen.Splash -> {
                        SplashScreen(
                            onSplashFinished = {
                                viewModel.navigateTo(Screen.Onboarding, addToBackStack = false)
                            }
                        )
                    }

                    is Screen.Onboarding -> {
                        OnboardingScreen(
                            onFinishOnboarding = {
                                viewModel.navigateTo(Screen.Login, addToBackStack = false)
                            },
                            onSkip = {
                                viewModel.switchBottomTab(BottomTab.HOME)
                            }
                        )
                    }

                    is Screen.Login -> {
                        LoginScreen(
                            onLoginSuccess = { phone ->
                                viewModel.loginUser(phone)
                                viewModel.switchBottomTab(BottomTab.ACCOUNT)
                            },
                            onSignUpSuccess = { name, phone, email ->
                                viewModel.signUpUser(name, phone, email)
                                viewModel.switchBottomTab(BottomTab.ACCOUNT)
                            },
                            onSkip = {
                                viewModel.switchBottomTab(BottomTab.HOME)
                            }
                        )
                    }

                    is Screen.Home -> {
                        HomeScreen(
                            products = allProducts,
                            wishlistIds = wishlistIds,
                            onProductClick = { viewModel.navigateTo(Screen.ProductDetails(it)) },
                            onCategoryClick = { catId, catName ->
                                viewModel.navigateTo(Screen.CategoryListing(catId, catName))
                            },
                            onToggleWishlist = { viewModel.toggleWishlist(it) },
                            onAddToCart = { viewModel.addToCart(it, 1) },
                            onViewAllDeals = { viewModel.switchBottomTab(BottomTab.DEALS) },
                            onViewOffers = { viewModel.navigateTo(Screen.Offers) }
                        )
                    }

                    is Screen.Categories -> {
                        CategoriesScreen(
                            products = allProducts,
                            wishlistIds = wishlistIds,
                            onProductClick = { viewModel.navigateTo(Screen.ProductDetails(it)) },
                            onToggleWishlist = { viewModel.toggleWishlist(it) },
                            onAddToCart = { viewModel.addToCart(it, 1) }
                        )
                    }

                    is Screen.Deals -> {
                        DealsScreen(
                            products = allProducts,
                            wishlistIds = wishlistIds,
                            onProductClick = { viewModel.navigateTo(Screen.ProductDetails(it)) },
                            onToggleWishlist = { viewModel.toggleWishlist(it) },
                            onAddToCart = { viewModel.addToCart(it, 1) }
                        )
                    }

                    is Screen.Cart -> {
                        CartScreen(
                            cartItems = cartItems,
                            subtotal = cartSubtotal,
                            mrpTotal = cartMrpTotal,
                            couponDiscount = couponDiscount,
                            finalTotal = cartFinalTotal,
                            appliedCoupon = appliedCoupon,
                            defaultAddress = defaultAddress,
                            onUpdateQuantity = { id, qty -> viewModel.updateCartQuantity(id, qty) },
                            onRemoveItem = { viewModel.removeFromCart(it) },
                            onApplyCoupon = { viewModel.applyCoupon(it) },
                            onRemoveCoupon = { viewModel.removeCoupon() },
                            onChangeAddress = { viewModel.navigateTo(Screen.DeliveryAddress(isForCheckout = false)) },
                            onProceedToCheckout = { viewModel.navigateTo(Screen.OrderSummary) },
                            onShopNow = { viewModel.switchBottomTab(BottomTab.HOME) }
                        )
                    }

                    is Screen.Account -> {
                        AccountScreen(
                            userProfile = userProfile,
                            isLoggedIn = isUserLoggedIn,
                            ordersCount = orders.size,
                            wishlistCount = wishlistProducts.size,
                            onLoginClick = { viewModel.navigateTo(Screen.Login) },
                            onLogoutClick = { viewModel.logoutUser() },
                            onNavigateToOrders = { viewModel.navigateTo(Screen.MyOrders) },
                            onNavigateToWishlist = { viewModel.navigateTo(Screen.Wishlist(isFromHome = false)) },
                            onNavigateToAddresses = { viewModel.navigateTo(Screen.DeliveryAddress(isForCheckout = false)) },
                            onNavigateToCoupons = { viewModel.navigateTo(Screen.Offers) },
                            onNavigateToProfileDetail = { viewModel.navigateTo(Screen.ProfileDetail) },
                            onNavigateToSettings = { viewModel.navigateTo(Screen.Settings) },
                            onNavigateToHelpSupport = { viewModel.navigateTo(Screen.HelpSupport) },
                            onNavigateToNotifications = { viewModel.navigateTo(Screen.Notifications) }
                        )
                    }

                    is Screen.ProductDetails -> {
                        val product = allProducts.find { it.id == screen.productId }
                        ProductDetailsScreen(
                            product = product,
                            isWishlisted = wishlistIds.contains(screen.productId),
                            reviewsFlow = viewModel.getReviewsForProduct(screen.productId),
                            relatedProducts = allProducts,
                            onToggleWishlist = { viewModel.toggleWishlist(screen.productId) },
                            onAddToCart = { viewModel.addToCart(screen.productId, 1) },
                            onBuyNow = {
                                viewModel.addToCart(screen.productId, 1)
                                viewModel.navigateTo(Screen.OrderSummary)
                            },
                            onWriteReview = { viewModel.navigateTo(Screen.CustomerReview(screen.productId)) },
                            onRelatedProductClick = { viewModel.navigateTo(Screen.ProductDetails(it)) },
                            onBack = { viewModel.goBack() }
                        )
                    }

                    is Screen.CategoryListing -> {
                        CategoryListingScreen(
                            categoryId = screen.categoryId,
                            categoryName = screen.categoryName,
                            products = allProducts,
                            wishlistIds = wishlistIds,
                            onProductClick = { viewModel.navigateTo(Screen.ProductDetails(it)) },
                            onToggleWishlist = { viewModel.toggleWishlist(it) },
                            onAddToCart = { viewModel.addToCart(it, 1) },
                            onBack = { viewModel.goBack() }
                        )
                    }

                    is Screen.SearchResults -> {
                        SearchResultsScreen(
                            query = screen.query,
                            products = allProducts,
                            wishlistIds = wishlistIds,
                            onProductClick = { viewModel.navigateTo(Screen.ProductDetails(it)) },
                            onToggleWishlist = { viewModel.toggleWishlist(it) },
                            onAddToCart = { viewModel.addToCart(it, 1) },
                            onBack = { viewModel.goBack() }
                        )
                    }

                    is Screen.Wishlist -> {
                        WishlistScreen(
                            wishlistProducts = wishlistProducts,
                            onProductClick = { viewModel.navigateTo(Screen.ProductDetails(it)) },
                            onRemoveFromWishlist = { viewModel.toggleWishlist(it) },
                            onMoveToCart = {
                                viewModel.addToCart(it, 1)
                                viewModel.toggleWishlist(it)
                            },
                            onBack = { viewModel.goBack() },
                            onExploreShop = { viewModel.switchBottomTab(BottomTab.HOME) }
                        )
                    }

                    is Screen.DeliveryAddress -> {
                        DeliveryAddressScreen(
                            addresses = addresses,
                            isForCheckout = screen.isForCheckout,
                            onSelectDefaultAddress = { viewModel.setDefaultAddress(it) },
                            onAddAddress = { name, phone, house, street, city, state, pin, type, isDef ->
                                viewModel.addAddress(name, phone, house, street, city, state, pin, type, isDef)
                            },
                            onDeleteAddress = { viewModel.deleteAddress(it) },
                            onContinueToSummary = { viewModel.navigateTo(Screen.OrderSummary) },
                            onBack = { viewModel.goBack() }
                        )
                    }

                    is Screen.OrderSummary -> {
                        OrderSummaryScreen(
                            cartItems = cartItems,
                            subtotal = cartSubtotal,
                            mrpTotal = cartMrpTotal,
                            couponDiscount = couponDiscount,
                            finalTotal = cartFinalTotal,
                            appliedCoupon = appliedCoupon,
                            address = defaultAddress,
                            onChangeAddress = { viewModel.navigateTo(Screen.DeliveryAddress(isForCheckout = true)) },
                            onProceedToPayment = { viewModel.navigateTo(Screen.Payment) },
                            onBack = { viewModel.goBack() }
                        )
                    }

                    is Screen.Payment -> {
                        PaymentScreen(
                            finalTotal = cartFinalTotal,
                            selectedPaymentMethod = selectedPaymentMethod,
                            onSelectPaymentMethod = { viewModel.setPaymentMethod(it) },
                            onPaymentSuccess = {
                                viewModel.placeOrder { orderId ->
                                    viewModel.navigateTo(Screen.OrderPlaced(orderId), addToBackStack = false)
                                }
                            },
                            onBack = { viewModel.goBack() }
                        )
                    }

                    is Screen.OrderPlaced -> {
                        OrderPlacedScreen(
                            orderId = screen.orderId,
                            onTrackOrder = { orderId ->
                                viewModel.navigateTo(Screen.TrackOrder(orderId))
                            },
                            onContinueShopping = {
                                viewModel.switchBottomTab(BottomTab.HOME)
                            }
                        )
                    }

                    is Screen.MyOrders -> {
                        MyOrdersScreen(
                            orders = orders,
                            onTrackOrder = { viewModel.navigateTo(Screen.TrackOrder(it)) },
                            onBack = { viewModel.goBack() },
                            onExploreShop = { viewModel.switchBottomTab(BottomTab.HOME) }
                        )
                    }

                    is Screen.TrackOrder -> {
                        val order = orders.find { it.orderId == screen.orderId }
                        TrackOrderScreen(
                            order = order,
                            onBack = { viewModel.goBack() },
                            onContactSupport = { viewModel.navigateTo(Screen.HelpSupport) }
                        )
                    }

                    is Screen.CustomerReview -> {
                        val product = allProducts.find { it.id == screen.productId }
                        CustomerReviewScreen(
                            product = product,
                            onSubmitReview = { rating, comment, hasPhoto ->
                                viewModel.submitReview(screen.productId, rating, comment, hasPhoto)
                            },
                            onBack = { viewModel.goBack() }
                        )
                    }

                    is Screen.ProfileDetail -> {
                        ProfileDetailScreen(
                            userProfile = userProfile,
                            onSaveProfile = { name, phone, email, pin, city ->
                                viewModel.updateProfile(name, phone, email, pin, city)
                            },
                            onBack = { viewModel.goBack() }
                        )
                    }

                    is Screen.Settings -> {
                        SettingsScreen(
                            isHindi = isHindi,
                            isDarkMode = isDarkMode,
                            onToggleLanguage = { viewModel.toggleLanguage() },
                            onToggleDarkMode = { viewModel.toggleDarkMode() },
                            onBack = { viewModel.goBack() }
                        )
                    }

                    is Screen.HelpSupport -> {
                        HelpSupportScreen(
                            onBack = { viewModel.goBack() }
                        )
                    }

                    is Screen.Offers -> {
                        OffersScreen(
                            onApplyCoupon = {
                                viewModel.applyCoupon(it)
                                viewModel.switchBottomTab(BottomTab.CART)
                            },
                            onBack = { viewModel.goBack() }
                        )
                    }

                    is Screen.Notifications -> {
                        NotificationsScreen(
                            notifications = notifications,
                            onMarkAllRead = { viewModel.markNotificationsRead() },
                            onBack = { viewModel.goBack() }
                        )
                    }

                    else -> {}
                }
            }
        }
    }
}
