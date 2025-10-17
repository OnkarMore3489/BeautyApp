package com.example.beautyapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    // Navigate to next screen after delay
    LaunchedEffect(Unit) {
        delay(3000)
        onSplashFinished()
    }

    // Layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            // App Name - BeautyFox
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Beauty",
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF007AFF) // Blue shade like 'yes'
                )
                Text(
                    text = "Fox",
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE91E63) // Pink shade like 'madam'
                )
            }

            // Tagline
            Text(
                text = "Salon Expert ki Home Delivery",
                fontSize = 18.sp,
                color = Color.Black,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Divider
            Divider(
                color = Color.Black.copy(alpha = 0.2f),
                thickness = 1.dp,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .width(180.dp)
            )

            // Subtext
            Text(
                text = "10 lakh+ Happy Customers",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
