package com.example.data.sample

import com.example.data.model.AddressEntity
import com.example.data.model.Category
import com.example.data.model.Coupon
import com.example.data.model.NotificationEntity
import com.example.data.model.OrderEntity
import com.example.data.model.ProductEntity
import com.example.data.model.ReviewEntity
import com.example.data.model.UserProfileEntity

object CatalogData {

    val categories = listOf(
        Category(
            id = "cat_women",
            name = "Women Fashion",
            hindiName = "महिला फैशन",
            iconName = "checkroom",
            itemCount = 142,
            bannerText = "Sarees, Kurtis & Lehengas up to 70% Off"
        ),
        Category(
            id = "cat_men",
            name = "Men Fashion",
            hindiName = "पुरुष फैशन",
            iconName = "dry_cleaning",
            itemCount = 98,
            bannerText = "Casual Shirts, Denim & Kurtas"
        ),
        Category(
            id = "cat_electronics",
            name = "Electronics",
            hindiName = "इलेक्ट्रॉनिक्स",
            iconName = "devices",
            itemCount = 76,
            bannerText = "Earbuds, Smartwatches & Accessories"
        ),
        Category(
            id = "cat_home",
            name = "Home & Kitchen",
            hindiName = "घर और रसोई",
            iconName = "kitchen",
            itemCount = 110,
            bannerText = "Cookware, Decor & Bedsheets"
        ),
        Category(
            id = "cat_beauty",
            name = "Beauty & Care",
            hindiName = "ब्यूटी और ग्रूमिंग",
            iconName = "spa",
            itemCount = 85,
            bannerText = "Skincare, Serums & Fragrances"
        ),
        Category(
            id = "cat_footwear",
            name = "Footwear",
            hindiName = "जूते और चप्पल",
            iconName = "roller_skating",
            itemCount = 64,
            bannerText = "Sneakers, Kolhapuris & Formal Shoes"
        ),
        Category(
            id = "cat_under99",
            name = "Under ₹99",
            hindiName = "₹99 से कम",
            iconName = "savings",
            itemCount = 45,
            bannerText = "Pocket-Friendly Daily Essentials"
        ),
        Category(
            id = "cat_under499",
            name = "Under ₹499",
            hindiName = "₹499 स्टोर",
            iconName = "local_offer",
            itemCount = 92,
            bannerText = "Best Value Fashion & Accessories"
        )
    )

    val coupons = listOf(
        Coupon(
            code = "MULFIRST",
            discountDescription = "Flat ₹150 OFF on first order above ₹499",
            minOrderAmount = 499.0,
            discountAmount = 150.0,
            isPercentage = false
        ),
        Coupon(
            code = "FESTIVE100",
            discountDescription = "Save ₹100 on orders above ₹699",
            minOrderAmount = 699.0,
            discountAmount = 100.0,
            isPercentage = false
        ),
        Coupon(
            code = "SUPER50",
            discountDescription = "Flat ₹50 OFF on orders above ₹299",
            minOrderAmount = 299.0,
            discountAmount = 50.0,
            isPercentage = false
        ),
        Coupon(
            code = "SAVE10",
            discountDescription = "10% Instant Discount up to ₹300",
            minOrderAmount = 999.0,
            discountAmount = 100.0,
            isPercentage = true,
            percentageValue = 10
        )
    )

    val initialProducts: List<ProductEntity> by lazy {
        ProductCatalogGenerator.generate1000Products()
    }

    val initialReviews = listOf(
        ReviewEntity(
            productId = "prod_01",
            userName = "Priya Sharma",
            rating = 5,
            comment = "The Banarasi saree is breathtaking! Rich zari finish looks like a 10,000 rupee saree from showroom. Wore it for Diwali and received countless compliments.",
            date = "22 Sep 2026",
            hasPhoto = true
        ),
        ReviewEntity(
            productId = "prod_01",
            userName = "Sunita Verma",
            rating = 4,
            comment = "Great fabric quality and very light to drape. Delivery arrived in just 24 hours in Delhi!",
            date = "15 Sep 2026",
            hasPhoto = false
        ),
        ReviewEntity(
            productId = "prod_07",
            userName = "Rohit Kumar",
            rating = 5,
            comment = "ANC works remarkably well for this price point! Battery life lasted 4 days of continuous usage. 100% recommended.",
            date = "24 Sep 2026",
            hasPhoto = true
        ),
        ReviewEntity(
            productId = "prod_07",
            userName = "Ankit Gupta",
            rating = 5,
            comment = "Punchy bass, clear mics for office calls. Fast charging is super convenient.",
            date = "18 Sep 2026",
            hasPhoto = false
        )
    )

    val initialAddresses = listOf(
        AddressEntity(
            id = 1,
            name = "Amiyo Sarkar",
            phone = "+91 98765 43210",
            houseNo = "Flat 402, Lotus Residency",
            street = "Barakhamba Road, Connaught Place",
            city = "New Delhi",
            state = "Delhi",
            pincode = "110001",
            type = "HOME",
            isDefault = true
        ),
        AddressEntity(
            id = 2,
            name = "Amiyo Sarkar (Office)",
            phone = "+91 98765 43210",
            houseNo = "Building 3B, Tech Park",
            street = "Sector 62",
            city = "Noida",
            state = "Uttar Pradesh",
            pincode = "201301",
            type = "WORK",
            isDefault = false
        )
    )

    val initialOrders = listOf(
        OrderEntity(
            orderId = "MUL-98472",
            orderDate = System.currentTimeMillis() - 86400000L * 2, // 2 days ago
            status = "OUT_FOR_DELIVERY",
            totalAmount = 1499.0,
            subtotal = 1649.0,
            discount = 150.0,
            deliveryFee = 0.0,
            paymentMethod = "Google Pay UPI",
            deliveryAddress = "Flat 402, Lotus Residency, Barakhamba Road, New Delhi 110001",
            itemsSummary = "1x Wireless Earbuds with Active Noise Cancellation (40H Playback)",
            courierName = "Mul Express Logistics",
            trackingNumber = "MUL-DEL-778210"
        ),
        OrderEntity(
            orderId = "MUL-81203",
            orderDate = System.currentTimeMillis() - 86400000L * 7, // 7 days ago
            status = "DELIVERED",
            totalAmount = 849.0,
            subtotal = 899.0,
            discount = 50.0,
            deliveryFee = 0.0,
            paymentMethod = "PhonePe UPI",
            deliveryAddress = "Flat 402, Lotus Residency, Barakhamba Road, New Delhi 110001",
            itemsSummary = "1x Pure Cotton Printed Anarkali Kurta with Pant & Dupatta",
            courierName = "Mul Express Logistics",
            trackingNumber = "MUL-DEL-654921"
        )
    )

    val initialNotifications = listOf(
        NotificationEntity(
            title = "🎉 Mega Festive Sale is LIVE!",
            message = "Up to 80% off on Sarees, Kurtas, Smartwatches & Kitchen appliances. Use code FESTIVE100 for extra ₹100 off!",
            timestamp = System.currentTimeMillis() - 3600000L * 3,
            isRead = false,
            type = "PROMO"
        ),
        NotificationEntity(
            title = "🚚 Order Out for Delivery!",
            message = "Your Mul Shop Order #MUL-98472 is out for delivery today. Delivery agent: Rajesh Kumar (+91 98111 22334).",
            timestamp = System.currentTimeMillis() - 3600000L * 5,
            isRead = false,
            type = "ORDER"
        ),
        NotificationEntity(
            title = "⚡ Flash Deal Ending Soon!",
            message = "Under ₹99 store offers end in 3 hours. Grab foldable mobile stands and accessories before stocks run out!",
            timestamp = System.currentTimeMillis() - 86400000L,
            isRead = true,
            type = "PRICE_DROP"
        )
    )
}
