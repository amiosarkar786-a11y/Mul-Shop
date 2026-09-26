package com.example.data.sample

import com.example.data.model.ProductEntity
import kotlin.random.Random

object ProductCatalogGenerator {

    fun generate1000Products(): List<ProductEntity> {
        val products = mutableListOf<ProductEntity>()
        val random = Random(42) // Consistent seed for reproducible realistic catalog

        // 1. Women Fashion (200 items)
        val womenBrands = listOf("Kalamandir", "Jaipur Vastra", "Libas Luxe", "Biba Ethnic", "Aurelia", "W for Woman", "Soch Elegance", "FabIndia", "Rangriti", "Meena Bazaar")
        val womenTypes = listOf(
            "Banarasi Art Silk Zari Saree",
            "Kanjivaram Woven Border Saree",
            "Pure Chanderi Resham Work Saree",
            "Printed Georgette Daily Wear Saree",
            "Lucknowi Chikankari Embroidered Kurti",
            "Cotton Flared Anarkali Kurta Set with Dupatta",
            "Straight Fit Rayon Kurti with Palazzo",
            "Festive Velvet Embroidered Semi-Stitched Lehenga",
            "Mulmul Handblock Printed Kurta Set",
            "Chiffon Floral Printed Saree with Blouse",
            "Bandhani Traditional Rajasthani Saree",
            "Mirror Work Festive Anarkali Gown",
            "A-Line Cotton Tunic Top for Women",
            "Tussar Silk Party Wear Saree with Zari",
            "Organza Floral Embroidered Saree"
        )
        val womenColors = listOf("Crimson Red", "Royal Blue", "Emerald Green", "Blush Pink", "Mustard Yellow", "Peacock Teal", "Maroon", "Pastel Peach", "Navy Gold", "Lavender")
        val womenImages = listOf(
            "https://images.unsplash.com/photo-1610030469983-98e550d6193c?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1583391733956-3750e0ff4e8b?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1617627143750-d86bc21e42bb?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1563178406-4cdc2923acbc?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1572804013309-59a88b7e92f1?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1509631179647-0177331693ae?w=600&auto=format&fit=crop&q=80"
        )

        for (i in 1..200) {
            val brand = womenBrands[i % womenBrands.size]
            val type = womenTypes[i % womenTypes.size]
            val color = womenColors[(i * 3) % womenColors.size]
            val mrp = (899..4999 step 100).toList().let { it[random.nextInt(it.size)] }.toDouble()
            val discount = random.nextInt(45, 75)
            val price = (mrp * (100 - discount) / 100.0).coerceAtLeast(299.0)
            val img = womenImages[i % womenImages.size]

            products.add(
                ProductEntity(
                    id = "mul_women_${i.toString().padStart(3, '0')}",
                    name = "$brand $color $type",
                    categoryId = "cat_women",
                    categoryName = "Women Fashion",
                    brand = brand,
                    price = price.toInt().toDouble(),
                    mrp = mrp,
                    discountPercent = discount,
                    imageUrl = img,
                    drawableResName = if (i == 1) "img_hero_fashion" else "",
                    description = "Exquisite $color $type tailored from premium fabric. Features authentic craftsmanship, breathable comfort, and elegant finish. Comes with quality assurance from $brand.",
                    rating = (40 + random.nextInt(10)) / 10f,
                    reviewCount = 50 + random.nextInt(2500),
                    stock = random.nextInt(15, 60),
                    deliveryDays = random.nextInt(1, 3),
                    isFlashDeal = (i % 7 == 0),
                    isTodayDeal = (i % 5 == 0),
                    isSpecialOffer = (i % 6 == 0)
                )
            )
        }

        // 2. Men Fashion (180 items)
        val menBrands = listOf("Manyavar Style", "Highlander Jeans", "Roadster Club", "Peter England", "Mufti Casuals", "Wrangler India", "U.S. Polo Assn", "Van Heusen", "Arrow", "Allen Solly")
        val menTypes = listOf(
            "Pure Breathable Linen Mandarin Kurta",
            "Slim Fit Washed Stretch Denim Jeans",
            "Pure Combed Cotton Casual Shirt",
            "Formal Wrinkle-Free Office Trousers",
            "Regular Fit Solid Polo T-Shirt",
            "Nehru Jacket with Pocket Square",
            "Cotton Pathani Kurta Salwar Set",
            "Chino Trousers with Stretch Fabric",
            "Checked Casual Flannel Shirt",
            "Cotton Casual Round Neck Graphic Tee",
            "Slim Fit Formal Blazer for Men",
            "Comfort Fit Elastic Waist Trackpants",
            "Khadi Cotton Short Kurta for Men",
            "Lightweight Bomber Jacket for Men"
        )
        val menColors = listOf("Navy Blue", "Olive Green", "Charcoal Grey", "Pure White", "Beige Khaki", "Wine Red", "Sky Blue", "Classic Black", "Rust Orange", "Emerald")
        val menImages = listOf(
            "https://images.unsplash.com/photo-1598033129183-c4f50c736f10?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1542272604-780c96856592?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1602810318383-e386cc2a3ccf?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1617137984095-74e4e5e3613f?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1521572267360-ee0c2909d518?w=600&auto=format&fit=crop&q=80"
        )

        for (i in 1..180) {
            val brand = menBrands[i % menBrands.size]
            val type = menTypes[i % menTypes.size]
            val color = menColors[(i * 2) % menColors.size]
            val mrp = (799..3999 step 100).toList().let { it[random.nextInt(it.size)] }.toDouble()
            val discount = random.nextInt(40, 70)
            val price = (mrp * (100 - discount) / 100.0).coerceAtLeast(299.0)
            val img = menImages[i % menImages.size]

            products.add(
                ProductEntity(
                    id = "mul_men_${i.toString().padStart(3, '0')}",
                    name = "$brand $color $type",
                    categoryId = "cat_men",
                    categoryName = "Men Fashion",
                    brand = brand,
                    price = price.toInt().toDouble(),
                    mrp = mrp,
                    discountPercent = discount,
                    imageUrl = img,
                    description = "Modern and stylish $color $type by $brand. Designed with breathable natural fibers for all-day comfort and long-lasting durability.",
                    rating = (41 + random.nextInt(9)) / 10f,
                    reviewCount = 30 + random.nextInt(1800),
                    stock = random.nextInt(10, 50),
                    deliveryDays = random.nextInt(1, 3),
                    isFlashDeal = (i % 8 == 0),
                    isTodayDeal = (i % 6 == 0),
                    isUnder499 = (price < 499)
                )
            )
        }

        // 3. Electronics & Gadgets (150 items)
        val elecBrands = listOf("boAt Airdopes", "Noise ColorFit", "Mi Power", "Realme Tech", "OnePlus Nord", "Fire-Boltt", "Boult Audio", "pTron Studio", "Portronics", "Zebronics")
        val elecTypes = listOf(
            "TWS Earbuds with Active Noise Cancellation",
            "1.96\" AMOLED Bluetooth Calling Smartwatch",
            "20000mAh 22.5W Fast Charging Power Bank",
            "Wireless Neckband with 30H Playtime",
            "16W Portable Bluetooth Waterproof Speaker",
            "Fast 65W GaN Multi-Port USB-C Wall Charger",
            "Pro Gaming Wired Headset with 7.1 Surround",
            "Full HD 1080P USB Webcam with Dual Mic",
            "Stainless Steel Cordless Beard Trimmer",
            "RGB Mechanical Gaming Keyboard with Hot-Swap",
            "Wireless Ergonomic Silent Optical Mouse",
            "Smart WiFi Plug with Energy Monitoring",
            "10\" Ring Light with Tripod Stand for Video",
            "MagSafe Magnetic Wireless Power Bank"
        )
        val elecImages = listOf(
            "https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1609592424364-c46648757041?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1546435770-a3e426bf472b?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=600&auto=format&fit=crop&q=80"
        )

        for (i in 1..150) {
            val brand = elecBrands[i % elecBrands.size]
            val type = elecTypes[i % elecTypes.size]
            val mrp = (999..5999 step 200).toList().let { it[random.nextInt(it.size)] }.toDouble()
            val discount = random.nextInt(45, 75)
            val price = (mrp * (100 - discount) / 100.0).coerceAtLeast(349.0)
            val img = elecImages[i % elecImages.size]

            products.add(
                ProductEntity(
                    id = "mul_elec_${i.toString().padStart(3, '0')}",
                    name = "$brand $type",
                    categoryId = "cat_electronics",
                    categoryName = "Electronics",
                    brand = brand,
                    price = price.toInt().toDouble(),
                    mrp = mrp,
                    discountPercent = discount,
                    imageUrl = img,
                    description = "Official 1-Year Indian Manufacturer Warranty. Packed with latest chipset, fast charging, low latency mode, and certified durable build quality.",
                    rating = (42 + random.nextInt(8)) / 10f,
                    reviewCount = 100 + random.nextInt(4500),
                    stock = random.nextInt(20, 80),
                    deliveryDays = 1,
                    isFlashDeal = (i % 4 == 0),
                    isTodayDeal = (i % 3 == 0),
                    isSpecialOffer = true
                )
            )
        }

        // 4. Home & Kitchen (140 items)
        val homeBrands = listOf("Prestige Cook", "Hawkins Kitchen", "Bombay Dyeing", "Vedic Heritage", "Milton Home", "Pigeon Stove", "Cello Plast", "Wonderchef", "Spaces Living", "Borosil")
        val homeTypes = listOf(
            "Hard Anodised Induction Base Kadhai 2.5L",
            "Stainless Steel Triply Pressure Cooker 3L",
            "100% Pure Ayurvedic Copper Water Bottle 1000ml",
            "Glance 210 TC Glazed Cotton Double Bedsheet",
            "Non-Stick Granite Dosa Tawa 28cm",
            "Stainless Steel Insulated Lunch Box Set",
            "Set of 6 Borosilicate Glass Coffee Mugs",
            "Microfiber Floor Cleaning Spray Mop",
            "Traditional Brass Handcrafted Diya Set",
            "Aroma Essential Oil Diffuser with LED Light",
            "Heavy Velvet Blackout Room Curtains 7ft",
            "Multi-Tier Stainless Steel Kitchen Spice Rack",
            "Ergonomic Memory Foam Cervical Bed Pillow",
            "Electric Vegetable Chopper & Whisk"
        )
        val homeImages = listOf(
            "https://images.unsplash.com/photo-1584990347449-3973950b719c?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1602143407151-7111542de6e8?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1583847268964-b28dc8f51f92?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1513519245088-0e12902e5a38?w=600&auto=format&fit=crop&q=80"
        )

        for (i in 1..140) {
            val brand = homeBrands[i % homeBrands.size]
            val type = homeTypes[i % homeTypes.size]
            val mrp = (699..3999 step 100).toList().let { it[random.nextInt(it.size)] }.toDouble()
            val discount = random.nextInt(35, 68)
            val price = (mrp * (100 - discount) / 100.0).coerceAtLeast(199.0)
            val img = homeImages[i % homeImages.size]

            products.add(
                ProductEntity(
                    id = "mul_home_${i.toString().padStart(3, '0')}",
                    name = "$brand $type",
                    categoryId = "cat_home",
                    categoryName = "Home & Kitchen",
                    brand = brand,
                    price = price.toInt().toDouble(),
                    mrp = mrp,
                    discountPercent = discount,
                    imageUrl = img,
                    description = "Premium home and kitchen essential from $brand. Crafted using food-grade materials, durable construction, and elegant finish.",
                    rating = (41 + random.nextInt(9)) / 10f,
                    reviewCount = 40 + random.nextInt(1600),
                    stock = random.nextInt(15, 70),
                    deliveryDays = random.nextInt(1, 3),
                    isTodayDeal = (i % 5 == 0)
                )
            )
        }

        // 5. Beauty & Care (120 items)
        val beautyBrands = listOf("The Derma Co", "Mamaearth", "Biotique Herbal", "Plum Goodness", "WOW Skin Science", "MCaffeine", "Minimalist", "Dot & Key", "Sugar Cosmetics", "Khadi Natural")
        val beautyTypes = listOf(
            "10% Vitamin C Face Glow Serum 30ml",
            "Onion Seed Oil for Hair Fall Control 200ml",
            "Ultra Matte Long Lasting Liquid Lipstick Set",
            "Broad Spectrum SPF 50 PA++++ Sunscreen Gel",
            "Niacinamide 10% Blemish Removal Serum",
            "Raw Coffee Face Wash with Walnut Scrub",
            "Kumkumadi Ayurvedic Night Glow Face Oil",
            "Hyaluronic Acid Deep Hydrating Moisturizer",
            "Organic Ayurvedic Cold Pressed Coconut Oil",
            "Intense Charcoal Herbal 24H Waterproof Kajal",
            "French Luxury EDP Long Lasting Perfume 100ml",
            "Activated Charcoal Peel-Off Blackhead Mask"
        )
        val beautyImages = listOf(
            "https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1608248597359-57779b5c3ff0?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1598440947619-2c35fc9aa908?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1571781926291-c477ebfd024b?w=600&auto=format&fit=crop&q=80"
        )

        for (i in 1..120) {
            val brand = beautyBrands[i % beautyBrands.size]
            val type = beautyTypes[i % beautyTypes.size]
            val mrp = (399..1499 step 50).toList().let { it[random.nextInt(it.size)] }.toDouble()
            val discount = random.nextInt(25, 55)
            val price = (mrp * (100 - discount) / 100.0).coerceAtLeast(149.0)
            val img = beautyImages[i % beautyImages.size]

            products.add(
                ProductEntity(
                    id = "mul_beauty_${i.toString().padStart(3, '0')}",
                    name = "$brand $type",
                    categoryId = "cat_beauty",
                    categoryName = "Beauty & Care",
                    brand = brand,
                    price = price.toInt().toDouble(),
                    mrp = mrp,
                    discountPercent = discount,
                    imageUrl = img,
                    description = "Dermatologically tested formula free from parabens, sulfates, and harsh chemicals. Enriched with natural Indian actives for visible results.",
                    rating = (43 + random.nextInt(7)) / 10f,
                    reviewCount = 90 + random.nextInt(3200),
                    stock = random.nextInt(25, 100),
                    deliveryDays = 1,
                    isFlashDeal = (i % 6 == 0),
                    isUnder499 = (price < 499)
                )
            )
        }

        // 6. Footwear (90 items)
        val footBrands = listOf("Asian Shoes", "Kohlapur Heritage", "Sparx Sports", "Campus Active", "Bata India", "Red Tape", "Woodland Rugged", "Khadim's", "Liberty Footwear", "Paragon")
        val footTypes = listOf(
            "Ultra Lightweight Breathable Running Shoes",
            "Handcrafted Ethnic Leather Kolhapuri Chappals",
            "Casual High-Top White Sneakers for Men",
            "Traditional Jutti with Resham Embroidery",
            "Genuine Leather Formal Slip-On Loafers",
            "Air-Cushioned Shock Absorbing Walking Shoes",
            "Soft Padded Daily Wear Slide Sandals",
            "Ethnic Block Heel Festive Sandals for Women",
            "Waterproof EVA Cloud Comfort Flip Flops"
        )
        val footImages = listOf(
            "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1549298916-b41d501d3772?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1560769629-975ec94e6a86?w=600&auto=format&fit=crop&q=80"
        )

        for (i in 1..90) {
            val brand = footBrands[i % footBrands.size]
            val type = footTypes[i % footTypes.size]
            val mrp = (699..2999 step 100).toList().let { it[random.nextInt(it.size)] }.toDouble()
            val discount = random.nextInt(40, 70)
            val price = (mrp * (100 - discount) / 100.0).coerceAtLeast(249.0)
            val img = footImages[i % footImages.size]

            products.add(
                ProductEntity(
                    id = "mul_foot_${i.toString().padStart(3, '0')}",
                    name = "$brand $type",
                    categoryId = "cat_footwear",
                    categoryName = "Footwear",
                    brand = brand,
                    price = price.toInt().toDouble(),
                    mrp = mrp,
                    discountPercent = discount,
                    imageUrl = img,
                    description = "Ergonomic memory foam cushion insole with non-slip durable rubber sole. Engineered for everyday durability and Indian road conditions.",
                    rating = (42 + random.nextInt(8)) / 10f,
                    reviewCount = 60 + random.nextInt(2100),
                    stock = random.nextInt(15, 60),
                    deliveryDays = random.nextInt(1, 3),
                    isTodayDeal = (i % 4 == 0)
                )
            )
        }

        // 7. Under ₹99 Budget Store (60 items)
        val u99Names = listOf(
            "Foldable Multi-Angle Mobile Stand for Desk",
            "Silicone Cable Organizer Clips Set of 6",
            "Stainless Steel Keyring Multi-tool Bottle Opener",
            "Aroma Scented Soy Wax Tealight Candles (Pack of 12)",
            "Screen Cleaning Spray with Microfiber Wipe",
            "High-Speed Micro USB to Type-C OTG Adapter",
            "Anti-Slip Grip Mobile Ring Holder Kickstand",
            "Travel Toiletries Squeeze Bottle 60ml Set",
            "Stainless Steel Dual-Sided Nail Cutter with File",
            "Cotton Ankle Length Breathable Socks (Pair of 2)",
            "Heavy Duty Self Adhesive Wall Hooks (Pack of 6)",
            "Silicone Wire Protector Sleeves (Pack of 8)"
        )
        val u99Images = listOf(
            "https://images.unsplash.com/photo-1586105251261-72a756497a11?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1582845512747-e42001c95638?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1603006905003-be475563bc59?w=600&auto=format&fit=crop&q=80"
        )

        for (i in 1..60) {
            val name = u99Names[i % u99Names.size] + " (Vol. $i)"
            val price = (49..99 step 5).toList().let { it[random.nextInt(it.size)] }.toDouble()
            val mrp = (199..399 step 50).toList().let { it[random.nextInt(it.size)] }.toDouble()
            val discount = ((mrp - price) / mrp * 100).toInt()

            products.add(
                ProductEntity(
                    id = "mul_u99_${i.toString().padStart(3, '0')}",
                    name = name,
                    categoryId = "cat_under99",
                    categoryName = "Under ₹99",
                    brand = "Mul Pocket Deals",
                    price = price,
                    mrp = mrp,
                    discountPercent = discount,
                    imageUrl = u99Images[i % u99Images.size],
                    description = "Pocket-friendly everyday home and gadget essential. Top rated value for money with instant fast shipping.",
                    rating = (41 + random.nextInt(9)) / 10f,
                    reviewCount = 80 + random.nextInt(1200),
                    stock = random.nextInt(30, 150),
                    deliveryDays = 1,
                    isUnder99 = true,
                    isFlashDeal = (i % 3 == 0)
                )
            )
        }

        // 8. Under ₹499 Store (60 items)
        val u499Names = listOf(
            "Polarized UV400 Protection Wayfarer Sunglasses",
            "Men Genuine Hunter Leather RFID Bifold Wallet",
            "Wired In-Ear Deep Bass Earphones with Mic",
            "Vintage Ceramic Printed Coffee Mug 350ml",
            "Printed Cotton Canvas Casual Tote Bag",
            "Heavy Cotton Reversible Bucket Hat",
            "Stainless Steel Vacuum Insulated Flask 500ml",
            "Matte Finish Non-Slip Extended Gaming Mousepad",
            "Men Braided Reversible Casual Waist Belt",
            "Pure Cotton Multipurpose Printed Bandana Scarves"
        )
        val u499Images = listOf(
            "https://images.unsplash.com/photo-1511499767150-a48a237f0083?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1627123424574-724758594e93?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=600&auto=format&fit=crop&q=80",
            "https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?w=600&auto=format&fit=crop&q=80"
        )

        for (i in 1..60) {
            val name = u499Names[i % u499Names.size] + " - Edition $i"
            val price = (149..489 step 20).toList().let { it[random.nextInt(it.size)] }.toDouble()
            val mrp = (699..1499 step 100).toList().let { it[random.nextInt(it.size)] }.toDouble()
            val discount = ((mrp - price) / mrp * 100).toInt()

            products.add(
                ProductEntity(
                    id = "mul_u499_${i.toString().padStart(3, '0')}",
                    name = name,
                    categoryId = "cat_under499",
                    categoryName = "Under ₹499",
                    brand = "Mul BestValue",
                    price = price,
                    mrp = mrp,
                    discountPercent = discount,
                    imageUrl = u499Images[i % u499Images.size],
                    description = "Premium lifestyle product priced under ₹499. High grade materials and sleek design guaranteed to exceed expectations.",
                    rating = (43 + random.nextInt(7)) / 10f,
                    reviewCount = 120 + random.nextInt(2400),
                    stock = random.nextInt(25, 90),
                    deliveryDays = random.nextInt(1, 2),
                    isUnder499 = true,
                    isTodayDeal = (i % 4 == 0)
                )
            )
        }

        return products // Total: 200 + 180 + 150 + 140 + 120 + 90 + 60 + 60 = 1000 items!
    }
}
