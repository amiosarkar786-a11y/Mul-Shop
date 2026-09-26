package com.example.data.repository

import com.example.data.local.MulShopDatabase
import com.example.data.model.AddressEntity
import com.example.data.model.CartItemEntity
import com.example.data.model.CartItemWithProduct
import com.example.data.model.NotificationEntity
import com.example.data.model.OrderEntity
import com.example.data.model.ProductEntity
import com.example.data.model.ReviewEntity
import com.example.data.model.UserProfileEntity
import com.example.data.model.WishlistItemEntity
import com.example.data.sample.CatalogData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first

class MulShopRepository(private val database: MulShopDatabase) {

    private val productDao = database.productDao()
    private val cartDao = database.cartDao()
    private val wishlistDao = database.wishlistDao()
    private val addressDao = database.addressDao()
    private val orderDao = database.orderDao()
    private val reviewDao = database.reviewDao()
    private val notificationDao = database.notificationDao()
    private val userProfileDao = database.userProfileDao()

    suspend fun checkAndSeedInitialData() {
        val count = productDao.getProductCount()
        if (count < 1000) {
            productDao.insertAll(CatalogData.initialProducts)
        }
        val addrCount = addressDao.getAddressCount()
        if (addrCount == 0) {
            reviewDao.insertAll(CatalogData.initialReviews)
            CatalogData.initialAddresses.forEach { addressDao.insert(it) }
            CatalogData.initialOrders.forEach { orderDao.insert(it) }
            notificationDao.insertAll(CatalogData.initialNotifications)
            userProfileDao.insertOrUpdate(UserProfileEntity())
        }
    }

    // Products
    fun getAllProducts(): Flow<List<ProductEntity>> = productDao.getAllProducts()

    fun getProductById(id: String): Flow<ProductEntity?> = productDao.getProductById(id)

    fun getProductsByCategory(categoryId: String): Flow<List<ProductEntity>> =
        productDao.getProductsByCategory(categoryId)

    fun searchProducts(query: String): Flow<List<ProductEntity>> =
        productDao.searchProducts(query)

    fun getFlashDeals(): Flow<List<ProductEntity>> = productDao.getFlashDeals()

    fun getUnder99Products(): Flow<List<ProductEntity>> = productDao.getUnder99Products()

    fun getUnder499Products(): Flow<List<ProductEntity>> = productDao.getUnder499Products()

    // Cart with full product details
    fun getCartItemsWithProducts(): Flow<List<CartItemWithProduct>> {
        return combine(cartDao.getCartItems(), productDao.getAllProducts()) { cartItems, allProducts ->
            val productMap = allProducts.associateBy { it.id }
            cartItems.mapNotNull { cartItem ->
                productMap[cartItem.productId]?.let { product ->
                    CartItemWithProduct(cartItem, product)
                }
            }
        }
    }

    suspend fun addToCart(productId: String, quantity: Int = 1) {
        val existing = cartDao.getCartItems().first().find { it.productId == productId }
        if (existing != null) {
            cartDao.updateQuantity(productId, existing.quantity + quantity)
        } else {
            cartDao.insertOrUpdate(CartItemEntity(productId = productId, quantity = quantity))
        }
    }

    suspend fun updateCartQuantity(productId: String, quantity: Int) {
        if (quantity <= 0) {
            cartDao.delete(productId)
        } else {
            cartDao.updateQuantity(productId, quantity)
        }
    }

    suspend fun removeFromCart(productId: String) {
        cartDao.delete(productId)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }

    // Wishlist
    fun getWishlistProducts(): Flow<List<ProductEntity>> {
        return combine(wishlistDao.getWishlistItems(), productDao.getAllProducts()) { wishlistItems, allProducts ->
            val productMap = allProducts.associateBy { it.id }
            wishlistItems.mapNotNull { productMap[it.productId] }
        }
    }

    fun isWishlisted(productId: String): Flow<Boolean> = wishlistDao.isWishlisted(productId)

    suspend fun toggleWishlist(productId: String) {
        val currentWishlist = wishlistDao.getWishlistItems().first()
        val exists = currentWishlist.any { it.productId == productId }
        if (exists) {
            wishlistDao.delete(productId)
        } else {
            wishlistDao.insert(WishlistItemEntity(productId = productId))
        }
    }

    // Addresses
    fun getAllAddresses(): Flow<List<AddressEntity>> = addressDao.getAllAddresses()

    fun getDefaultAddress(): Flow<AddressEntity?> = addressDao.getDefaultAddress()

    suspend fun addAddress(address: AddressEntity) {
        val currentCount = addressDao.getAddressCount()
        val toInsert = if (currentCount == 0) address.copy(isDefault = true) else address
        val newId = addressDao.insert(toInsert)
        if (toInsert.isDefault) {
            addressDao.setAddressAsDefault(newId)
        }
    }

    suspend fun setDefaultAddress(addressId: Long) {
        addressDao.setAddressAsDefault(addressId)
    }

    suspend fun deleteAddress(address: AddressEntity) {
        addressDao.delete(address)
    }

    // Orders
    fun getAllOrders(): Flow<List<OrderEntity>> = orderDao.getAllOrders()

    fun getOrderById(orderId: String): Flow<OrderEntity?> = orderDao.getOrderById(orderId)

    suspend fun placeOrder(
        items: List<CartItemWithProduct>,
        totalAmount: Double,
        subtotal: Double,
        discount: Double,
        paymentMethod: String,
        deliveryAddress: String
    ): String {
        val randomNum = (10000..99999).random()
        val orderId = "MUL-$randomNum"
        val itemsSummary = items.joinToString(", ") { "${it.cartItem.quantity}x ${it.product.name}" }

        val order = OrderEntity(
            orderId = orderId,
            orderDate = System.currentTimeMillis(),
            status = "ORDER_PLACED",
            totalAmount = totalAmount,
            subtotal = subtotal,
            discount = discount,
            deliveryFee = 0.0,
            paymentMethod = paymentMethod,
            deliveryAddress = deliveryAddress,
            itemsSummary = itemsSummary,
            courierName = "Mul Express Logistics",
            trackingNumber = "MUL-DEL-$randomNum"
        )
        orderDao.insert(order)
        cartDao.clearCart()

        // Insert notification
        notificationDao.insert(
            NotificationEntity(
                title = "🎉 Order Confirmed: #$orderId",
                message = "Thank you for shopping on Mul Shop! Your order has been placed and will be delivered in 1-2 days.",
                timestamp = System.currentTimeMillis(),
                isRead = false,
                type = "ORDER"
            )
        )

        return orderId
    }

    // Reviews
    fun getReviewsForProduct(productId: String): Flow<List<ReviewEntity>> =
        reviewDao.getReviewsForProduct(productId)

    suspend fun submitReview(productId: String, userName: String, rating: Int, comment: String, hasPhoto: Boolean) {
        reviewDao.insert(
            ReviewEntity(
                productId = productId,
                userName = userName,
                rating = rating,
                comment = comment,
                date = "Today",
                hasPhoto = hasPhoto
            )
        )
    }

    // Notifications
    fun getAllNotifications(): Flow<List<NotificationEntity>> = notificationDao.getAllNotifications()

    suspend fun markAllNotificationsAsRead() {
        notificationDao.markAllAsRead()
    }

    // User Profile
    fun getUserProfile(): Flow<UserProfileEntity?> = userProfileDao.getUserProfile()

    suspend fun updateUserProfile(profile: UserProfileEntity) {
        userProfileDao.insertOrUpdate(profile)
    }
}
