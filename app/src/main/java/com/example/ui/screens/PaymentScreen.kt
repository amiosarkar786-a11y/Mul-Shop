package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.LocalAtm
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BackgroundCream
import com.example.ui.theme.PrimaryPink
import com.example.ui.theme.PrimaryYellow
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PaymentScreen(
    finalTotal: Double,
    selectedPaymentMethod: String,
    onSelectPaymentMethod: (String) -> Unit,
    onPaymentSuccess: () -> Unit,
    onBack: () -> Unit
) {
    var isProcessing by remember { mutableStateOf(false) }
    var upiIdInput by remember { mutableStateOf("amiyo@oksbi") }

    val paymentOptions = listOf(
        Triple("Google Pay", "Fast, Instant UPI Payment with Google Pay", Icons.Default.AccountBalanceWallet),
        Triple("PhonePe / Paytm", "Pay with PhonePe, Paytm, or BHIM UPI", Icons.Default.AccountBalanceWallet),
        Triple("UPI ID / Intent", "Enter any VPA (e.g. mobile@upi)", Icons.Default.Lock),
        Triple("Scan & Pay QR Code", "Scan dynamic UPI QR code", Icons.Default.QrCode2),
        Triple("Cash on Delivery (COD)", "Pay with Cash or UPI when order is delivered (₹0 Fee)", Icons.Default.LocalAtm)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 74.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack, modifier = Modifier.testTag("payment_back_btn")) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = TextPrimary
                    )
                }
                Text(
                    text = "Select Payment Method",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Amount to pay pill
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Amount to Pay",
                                    fontSize = 12.sp,
                                    color = TextSecondary
                                )
                                Text(
                                    text = "₹${finalTotal.toInt()}",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Black,
                                    color = TextPrimary
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFFE8F5E9))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "100% SECURE",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SuccessGreen
                                )
                            }
                        }
                    }
                }

                // Payment Options
                items(paymentOptions) { (title, subtitle, icon) ->
                    val isSelected = selectedPaymentMethod == title

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelectPaymentMethod(title) }
                            .testTag("payment_option_${title.lowercase().replace(" ", "_")}"),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, PrimaryPink) else null,
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { onSelectPaymentMethod(title) },
                                    colors = RadioButtonDefaults.colors(selectedColor = PrimaryPink)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(PrimaryPink.copy(alpha = 0.1f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = null,
                                        tint = PrimaryPink,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = subtitle,
                                        fontSize = 11.5.sp,
                                        color = TextSecondary
                                    )
                                }
                            }

                            // If UPI ID selected, show textfield
                            if (isSelected && title == "UPI ID / Intent") {
                                Spacer(modifier = Modifier.height(10.dp))
                                OutlinedTextField(
                                    value = upiIdInput,
                                    onValueChange = { upiIdInput = it },
                                    label = { Text("Enter UPI ID (e.g. mobile@upi)") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 36.dp),
                                    singleLine = true
                                )
                            }

                            // If QR Code selected, show QR demo preview
                            if (isSelected && title == "Scan & Pay QR Code") {
                                Spacer(modifier = Modifier.height(10.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 36.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFFF7F5F2))
                                        .padding(12.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            imageVector = Icons.Default.QrCode2,
                                            contentDescription = "QR Code",
                                            tint = TextPrimary,
                                            modifier = Modifier.size(100.dp)
                                        )
                                        Text(
                                            text = "Scan with any UPI App to Pay ₹${finalTotal.toInt()}",
                                            fontSize = 11.5.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextPrimary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Production Note Card
                item {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFFFFF8E1),
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryYellow)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "🔒 Secure Demo Mode Active",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = TextPrimary
                            )
                            Text(
                                text = "For production deployments, connect your verified Indian payment gateway (Razorpay / Cashfree / PhonePe PG). Tap 'Pay' to simulate an instant successful transaction.",
                                fontSize = 11.sp,
                                color = TextSecondary,
                                lineHeight = 15.sp,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }
            }
        }

        // Fixed Pay Now Bottom Bar
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding(),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "₹${finalTotal.toInt()}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = TextPrimary
                    )
                    Text(
                        text = selectedPaymentMethod,
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }

                Button(
                    onClick = {
                        if (!isProcessing) {
                            isProcessing = true
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(1200) // Realistic processing simulation
                                isProcessing = false
                                onPaymentSuccess()
                            }
                        }
                    },
                    modifier = Modifier
                        .height(48.dp)
                        .testTag("pay_now_btn"),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                    enabled = !isProcessing
                ) {
                    if (isProcessing) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.5.dp
                        )
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (selectedPaymentMethod.startsWith("Cash")) "Confirm Order" else "Pay ₹${finalTotal.toInt()} Now",
                                fontSize = 14.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
