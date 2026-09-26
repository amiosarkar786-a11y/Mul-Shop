package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.MulShopDatabase
import com.example.data.model.AddressEntity
import com.example.data.model.CartItemWithProduct
import com.example.data.model.Coupon
import com.example.data.model.NotificationEntity
import com.example.data.model.OrderEntity
import com.example.data.model.ProductEntity
import com.example.data.model.ReviewEntity
import com.example.data.model.UserProfileEntity
import com.example.data.repository.MulShopRepository
import com.example.data.sample.CatalogData
import com.example.ui.navigation.BottomTab
import com.example.ui.navigation.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MulShopViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MulShopRepository(MulShopDatabase.getDatabase(application))

    // Navigation state stack
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Splash)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val navStack = ArrayDeque<Screen>()

    private val _currentBottomTab = MutableStateFlow(BottomTab.HOME)
    val currentBottomTab: StateFlow<BottomTab> = _currentBottomTab.asStateFlow()

    // Data Streams
    val allProducts: StateFlow<List<ProductEntity>> = repository.getAllProducts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val cartItems: StateFlow<List<CartItemWithProduct>> = repository.getCartItemsWithProducts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val wishlistProducts: StateFlow<List<ProductEntity>> = repository.getWishlistProducts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val addresses: StateFlow<List<AddressEntity>> = repository.getAllAddresses()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val defaultAddress: StateFlow<AddressEntity?> = repository.getDefaultAddress()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val orders: StateFlow<List<OrderEntity>> = repository.getAllOrders()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val notifications: StateFlow<List<NotificationEntity>> = repository.getAllNotifications()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userProfile: StateFlow<UserProfileEntity?> = repository.getUserProfile()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // UI state
    private val _appliedCoupon = MutableStateFlow<Coupon?>(null)
    val appliedCoupon: StateFlow<Coupon?> = _appliedCoupon.asStateFlow()

    private val _selectedPaymentMethod = MutableStateFlow("Google Pay")
    val selectedPaymentMethod: StateFlow<String> = _selectedPaymentMethod.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategoryFilter = MutableStateFlow<String?>(null)
    val selectedCategoryFilter: StateFlow<String?> = _selectedCategoryFilter.asStateFlow()

    private val _selectedSort = MutableStateFlow("POPULAR")
    val selectedSort: StateFlow<String> = _selectedSort.asStateFlow()

    private val _deliveryLocation = MutableStateFlow("New Delhi - 110001")
    val deliveryLocation: StateFlow<String> = _deliveryLocation.asStateFlow()

    private val _isLanguageHindi = MutableStateFlow(false)
    val isLanguageHindi: StateFlow<Boolean> = _isLanguageHindi.asStateFlow()

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _isUserLoggedIn = MutableStateFlow(true)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn.asStateFlow()

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    fun loginUser(phone: String) {
        _isUserLoggedIn.value = true
        viewModelScope.launch {
            _toastEvent.emit("Welcome back to Mul Shop! 🎉")
        }
    }

    fun signUpUser(name: String, phone: String, email: String) {
        _isUserLoggedIn.value = true
        viewModelScope.launch {
            val updated = UserProfileEntity(
                name = name.ifBlank { "Amiyo Sarkar" },
                phone = phone.ifBlank { "+91 98765 43210" },
                email = email.ifBlank { "amiosarkar786@gmail.com" },
                defaultPincode = "110001",
                city = "New Delhi"
            )
            repository.updateUserProfile(updated)
            _toastEvent.emit("Account created successfully! Welcome to Mul Shop 🎉")
        }
    }

    fun logoutUser() {
        _isUserLoggedIn.value = false
        viewModelScope.launch {
            _toastEvent.emit("Logged out successfully 👋")
        }
    }

    init {
        viewModelScope.launch {
            repository.checkAndSeedInitialData()
        }
    }

    // Navigation Methods
    fun navigateTo(screen: Screen, addToBackStack: Boolean = true) {
        if (addToBackStack) {
            navStack.addLast(_currentScreen.value)
        }
        _currentScreen.value = screen

        // Sync bottom nav tab if navigating to a main tab
        when (screen) {
            is Screen.Home -> _currentBottomTab.value = BottomTab.HOME
            is Screen.Categories -> _currentBottomTab.value = BottomTab.CATEGORIES
            is Screen.Deals -> _currentBottomTab.value = BottomTab.DEALS
            is Screen.Cart -> _currentBottomTab.value = BottomTab.CART
            is Screen.Account -> _currentBottomTab.value = BottomTab.ACCOUNT
            else -> {}
        }
    }

    fun goBack(): Boolean {
        if (navStack.isNotEmpty()) {
            val previous = navStack.removeLast()
            _currentScreen.value = previous
            when (previous) {
                is Screen.Home -> _currentBottomTab.value = BottomTab.HOME
                is Screen.Categories -> _currentBottomTab.value = BottomTab.CATEGORIES
                is Screen.Deals -> _currentBottomTab.value = BottomTab.DEALS
                is Screen.Cart -> _currentBottomTab.value = BottomTab.CART
                is Screen.Account -> _currentBottomTab.value = BottomTab.ACCOUNT
                else -> {}
            }
            return true
        }
        return false
    }

    fun switchBottomTab(tab: BottomTab) {
        _currentBottomTab.value = tab
        val targetScreen = when (tab) {
            BottomTab.HOME -> Screen.Home
            BottomTab.CATEGORIES -> Screen.Categories
            BottomTab.DEALS -> Screen.Deals
            BottomTab.CART -> Screen.Cart
            BottomTab.ACCOUNT -> Screen.Account
        }
        // Switch root screen without ballooning the back stack
        navStack.clear()
        _currentScreen.value = targetScreen
    }

    // Cart Calculation
    val cartSubtotal: StateFlow<Double> = cartItems.combine(_appliedCoupon) { items, _ ->
        items.sumOf { it.product.price * it.cartItem.quantity }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val cartMrpTotal: StateFlow<Double> = cartItems.combine(_appliedCoupon) { items, _ ->
        items.sumOf { it.product.mrp * it.cartItem.quantity }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val couponDiscount: StateFlow<Double> = cartItems.combine(_appliedCoupon) { items, coupon ->
        if (coupon == null) return@combine 0.0
        val subtotal = items.sumOf { it.product.price * it.cartItem.quantity }
        if (subtotal >= coupon.minOrderAmount) {
            if (coupon.isPercentage) {
                (subtotal * coupon.percentageValue / 100.0).coerceAtMost(300.0)
            } else {
                coupon.discountAmount
            }
        } else 0.0
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val cartFinalTotal: StateFlow<Double> = combine(cartSubtotal, couponDiscount) { subtotal, discount ->
        (subtotal - discount).coerceAtLeast(0.0)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    // User Operations
    fun addToCart(productId: String, quantity: Int = 1) {
        viewModelScope.launch {
            repository.addToCart(productId, quantity)
            _toastEvent.emit("Added to Cart 🛍️")
        }
    }

    fun updateCartQuantity(productId: String, quantity: Int) {
        viewModelScope.launch {
            repository.updateCartQuantity(productId, quantity)
        }
    }

    fun removeFromCart(productId: String) {
        viewModelScope.launch {
            repository.removeFromCart(productId)
            _toastEvent.emit("Item removed from Cart")
        }
    }

    fun toggleWishlist(productId: String) {
        viewModelScope.launch {
            repository.toggleWishlist(productId)
        }
    }

    fun isWishlisted(productId: String) = repository.isWishlisted(productId)

    fun applyCoupon(code: String) {
        val coupon = CatalogData.coupons.find { it.code.equals(code.trim(), ignoreCase = true) }
        if (coupon != null) {
            val subtotal = cartSubtotal.value
            if (subtotal >= coupon.minOrderAmount) {
                _appliedCoupon.value = coupon
                viewModelScope.launch {
                    _toastEvent.emit("Coupon '${coupon.code}' applied successfully! 🎉")
                }
            } else {
                viewModelScope.launch {
                    _toastEvent.emit("Min order ₹${coupon.minOrderAmount.toInt()} required for this coupon")
                }
            }
        } else {
            viewModelScope.launch {
                _toastEvent.emit("Invalid coupon code")
            }
        }
    }

    fun removeCoupon() {
        _appliedCoupon.value = null
        viewModelScope.launch {
            _toastEvent.emit("Coupon removed")
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCategoryFilter(categoryId: String?) {
        _selectedCategoryFilter.value = categoryId
    }

    fun setSort(sort: String) {
        _selectedSort.value = sort
    }

    fun setPaymentMethod(method: String) {
        _selectedPaymentMethod.value = method
    }

    fun setDeliveryLocation(location: String) {
        _deliveryLocation.value = location
    }

    fun toggleLanguage() {
        _isLanguageHindi.value = !_isLanguageHindi.value
    }

    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }

    // Address
    fun addAddress(name: String, phone: String, houseNo: String, street: String, city: String, state: String, pincode: String, type: String, isDefault: Boolean) {
        viewModelScope.launch {
            val address = AddressEntity(
                name = name,
                phone = phone,
                houseNo = houseNo,
                street = street,
                city = city,
                state = state,
                pincode = pincode,
                type = type,
                isDefault = isDefault
            )
            repository.addAddress(address)
            _toastEvent.emit("Address saved successfully!")
        }
    }

    fun setDefaultAddress(addressId: Long) {
        viewModelScope.launch {
            repository.setDefaultAddress(addressId)
            _toastEvent.emit("Delivery address updated")
        }
    }

    fun deleteAddress(address: AddressEntity) {
        viewModelScope.launch {
            repository.deleteAddress(address)
            _toastEvent.emit("Address deleted")
        }
    }

    // Order Placement
    fun placeOrder(onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            val items = cartItems.value
            if (items.isEmpty()) {
                _toastEvent.emit("Your cart is empty")
                return@launch
            }

            val address = defaultAddress.value
            val addressStr = if (address != null) {
                "${address.name}, ${address.houseNo}, ${address.street}, ${address.city} - ${address.pincode}"
            } else {
                "Flat 402, Lotus Residency, Connaught Place, New Delhi - 110001"
            }

            val orderId = repository.placeOrder(
                items = items,
                totalAmount = cartFinalTotal.value,
                subtotal = cartSubtotal.value,
                discount = couponDiscount.value + (cartMrpTotal.value - cartSubtotal.value),
                paymentMethod = selectedPaymentMethod.value,
                deliveryAddress = addressStr
            )

            _appliedCoupon.value = null
            onSuccess(orderId)
        }
    }

    // Reviews
    fun getReviewsForProduct(productId: String) = repository.getReviewsForProduct(productId)

    fun submitReview(productId: String, rating: Int, comment: String, hasPhoto: Boolean) {
        viewModelScope.launch {
            val userName = userProfile.value?.name ?: "Customer"
            repository.submitReview(productId, userName, rating, comment, hasPhoto)
            _toastEvent.emit("Review submitted! Thank you for your feedback ⭐")
        }
    }

    // Profile
    fun updateProfile(name: String, phone: String, email: String, pincode: String, city: String) {
        viewModelScope.launch {
            val updated = UserProfileEntity(
                name = name,
                phone = phone,
                email = email,
                defaultPincode = pincode,
                city = city
            )
            repository.updateUserProfile(updated)
            _deliveryLocation.value = "$city - $pincode"
            _toastEvent.emit("Profile updated successfully")
        }
    }

    fun markNotificationsRead() {
        viewModelScope.launch {
            repository.markAllNotificationsAsRead()
        }
    }
}
