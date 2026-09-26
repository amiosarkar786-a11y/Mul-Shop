package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.sample.CatalogData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Mul Shop", appName)
    }

    @Test
    fun `catalog has products and categories`() {
        assertTrue(CatalogData.categories.isNotEmpty())
        assertEquals(1000, CatalogData.initialProducts.size)
        assertTrue(CatalogData.coupons.any { it.code == "MULFIRST" })
    }
}
