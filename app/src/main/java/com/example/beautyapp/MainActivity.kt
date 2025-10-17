package com.example.beautyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.beautyapp.ui.navigation.AppNavHost
import com.example.beautyapp.ui.screens.ExistingOtpScreen
import com.example.beautyapp.ui.screens.FullScreenLoginPage
import com.example.beautyapp.ui.screens.ProfileScreen
import com.example.beautyapp.ui.screens.SplashScreen
import com.example.beautyapp.ui.theme.BeautyAppTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Hash code
//        OS65xtUjRMg
        setContent {
            BeautyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    // App-wide login state
                    var showSplash by remember { mutableStateOf(true) }
                    var isLoggedIn by remember { mutableStateOf(false) }
                    var showLoginScreen by remember { mutableStateOf(true) }
                    var showOtpScreen by remember { mutableStateOf(false) }
                    var mobileNumberForOtp by remember { mutableStateOf("") }

                    when {
                        showSplash -> {
                            SplashScreen(onSplashFinished = { showSplash = false })
                        }

                        showLoginScreen && !showOtpScreen -> {
                            FullScreenLoginPage(
                                onGetOtp = { mobile ->
                                    mobileNumberForOtp = mobile
                                    showOtpScreen = true
                                },
                                onSkip = {
                                    isLoggedIn = false
                                    showLoginScreen = false
                                }
                            )
                        }
                        showOtpScreen -> {
                            ExistingOtpScreen(
                                mobileNumber = mobileNumberForOtp,
                                onLoginSuccess = {
                                    isLoggedIn = true
                                    showLoginScreen = false
                                    showOtpScreen = false
                                },
                                onBack = {
                                    // ✅ Go back to login screen
                                    showOtpScreen = false
                                    showLoginScreen = true
                                }
                            )
                        }
                        else -> {
                            // Show main app once logged in or skipped
                            AppNavHost(
                                navController = navController,
                                isLoggedIn = isLoggedIn,
                                onLoginSuccess = { isLoggedIn = true },
                                onLogout = { isLoggedIn = false }
                            )
                        }
                    }
                }
            }
        }
    }
}

