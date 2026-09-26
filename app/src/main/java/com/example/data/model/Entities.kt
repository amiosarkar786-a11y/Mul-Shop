package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val categoryId: String,
    val categoryName: String,
    val brand: String,
    val price: Double,
    val mrp: Double,
    val discountPercent: Int,
    val imageUrl: String,
    val drawableResName: String = "",
    val description: String,
    val rating: Float,
    val reviewCount: Int,
    val stock: Int = 25,
    val deliveryDays: Int = 1,
    val isFlashDeal: Boolean = false,
    val isUnder99: Boolean = false,
    val isUnder499: Boolean = false,
    val isTodayDeal: Boolean = false,
    val isSpecialOffer: Boolean = false
)

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val productId: String,
    val quantity: Int = 1,
    val addedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "wishlist_items")
data class WishlistItemEntity(
    @PrimaryKey val productId: String,
    val addedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "addresses")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val phone: String,
    val houseNo: String,
    val street: String,
    val city: String,
    val state: String,
    val pincode: String,
    val type: String = "HOME", // HOME or WORK
    val isDefault: Boolean = false
)

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val orderId: String,
    val orderDate: Long = System.currentTimeMillis(),
    val status: String = "ORDER_PLACED", // ORDER_PLACED, CONFIRMED, SHIPPED, OUT_FOR_DELIVERY, DELIVERED
    val totalAmount: Double,
    val subtotal: Double,
    val discount: Double,
    val deliveryFee: Double = 0.0,
    val paymentMethod: String,
    val deliveryAddress: String,
    val itemsSummary: String, // JSON or formatted summary of items
    val courierName: String = "Mul Express Logistics",
    val trackingNumber: String = ""
)

@Entity(tableName = "reviews")
data class ReviewEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: String,
    val userName: String,
    val rating: Int,
    val comment: String,
    val date: String,
    val hasPhoto: Boolean = false
)

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false,
    val type: String = "PROMO" // ORDER, PROMO, PRICE_DROP
)

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: String = "primary_user",
    val name: String = "Amiyo Sarkar",
    val phone: String = "+91 98765 43210",
    val email: String = "amiosarkar786@gmail.com",
    val defaultPincode: String = "110001",
    val city: String = "New Delhi",
    val profilePhotoUri: String = ""
)

// UI and Composite domain models
data class CartItemWithProduct(
    val cartItem: CartItemEntity,
    val product: ProductEntity
)

data class Category(
    val id: String,
    val name: String,
    val hindiName: String,
    val iconName: String,
    val itemCount: Int,
    val bannerText: String
)

data class Coupon(
    val code: String,
    val discountDescription: String,
    val minOrderAmount: Double,
    val discountAmount: Double,
    val isPercentage: Boolean = false,
    val percentageValue: Int = 0,
    val expiry: String = "Valid till 31 Oct"
)
