package com.example.ui.navigation

sealed class Screen {
    object Splash : Screen()
    object Onboarding : Screen()
    object Login : Screen()
    
    // Bottom Nav Tabs
    object Home : Screen()
    object Categories : Screen()
    object Deals : Screen()
    object Cart : Screen()
    object Account : Screen()
    
    // Sub-screens & flows
    data class ProductDetails(val productId: String) : Screen()
    data class CategoryListing(val categoryId: String, val categoryName: String) : Screen()
    data class SearchResults(val query: String = "") : Screen()
    data class Wishlist(val isFromHome: Boolean = false) : Screen()
    data class DeliveryAddress(val isForCheckout: Boolean = false) : Screen()
    object OrderSummary : Screen()
    object Payment : Screen()
    data class OrderPlaced(val orderId: String) : Screen()
    object MyOrders : Screen()
    data class TrackOrder(val orderId: String) : Screen()
    data class CustomerReview(val productId: String) : Screen()
    object ProfileDetail : Screen()
    object Settings : Screen()
    object HelpSupport : Screen()
    object Offers : Screen()
    object Notifications : Screen()
    data class BrandCollection(val brandName: String) : Screen()
}

enum class BottomTab {
    HOME,
    CATEGORIES,
    DEALS,
    CART,
    ACCOUNT
}
