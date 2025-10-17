package com.example.beautyapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.beautyapp.R

@Composable
fun Booking(navController: NavController,isLoggedIn: Boolean,onLoginSuccess: () -> Unit) {
    MyAppointmentsScreen(navController, isLoggedIn,onLoginSuccess)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppointmentsScreen(
    navController: NavController,
    isLoggedIn: Boolean,
    onLoginSuccess: () -> Unit
) {
    var showSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F3F8)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = "My Appointments",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        )

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val image: Painter = painterResource(id = R.drawable.aa)
            Image(
                painter = image,
                contentDescription = "No bookings found",
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Fit
            )

            // Title Text
            Text(
                text = if (isLoggedIn) "No bookings found" else "No bookings yet!",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Description Text
            Text(
                text = if (isLoggedIn)
                    "Looks like you haven't placed any booking yet!"
                else
                    "Looks like you haven't Logged in yet!",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = if (!isLoggedIn) 24.dp else 0.dp)
            )

            // Show Login/Sign Up button if not logged in
            if (!isLoggedIn) {
                Button(
                    onClick = { showSheet = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(220.dp)
                        .height(50.dp)
                ) {
                    Text(
                        text = "Login / Sign Up",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            if (showSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showSheet = false },
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    scrimColor = Color.Black.copy(alpha = 0.5f)
                ) {
                    // Track OTP flow inside the sheet
                    var showOtpSheet by remember { mutableStateOf(false) }
                    var mobileNumberForOtp by remember { mutableStateOf("") }

                    if (!showOtpSheet) {
                        FullScreenLoginPage(
                            onGetOtp = { mobile ->
                                mobileNumberForOtp = mobile
                                showOtpSheet = true // navigate to OTP screen
                            },
                            onSkip = null // ❌ skip button hidden in bottom sheet
                        )
                    }
                    else {
                        ExistingOtpScreen(
                            mobileNumber = mobileNumberForOtp,
                            onLoginSuccess = {
                                showSheet = false
                                onLoginSuccess() // ✅ update login state after OTP verify
                            },
                            onBack = {
                                showOtpSheet = false // ✅ go back to login inside the sheet
                            }
                        )
                    }
                }
            }
        }
    }
}
