package com.example.beautyapp.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.beautyapp.ui.screens.Account
import com.example.beautyapp.ui.screens.Booking
import com.example.beautyapp.ui.screens.CustomBottomNavigationBar
import com.example.beautyapp.ui.screens.ExistingOtpScreen
import com.example.beautyapp.ui.screens.HomeScreen
import com.example.beautyapp.ui.screens.LocationScreen
import com.example.beautyapp.ui.screens.MenHomeScreen
import com.example.beautyapp.ui.screens.ProfileScreen
import com.example.beautyapp.ui.screens.ServiceSearchScreen
@SuppressLint("SuspiciousIndentation")
@Composable
fun AppNavHost(
    navController: NavHostController,
    isLoggedIn: Boolean,
    onLoginSuccess: () -> Unit,
    onLogout: () -> Unit
) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route
    val screensWithBottomNav = listOf("home", "men", "account", "booking")

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // Regular Screens
        composable("home") { HomeScreen(navController) }
        composable("men") { MenHomeScreen(navController) }
        composable("location") { LocationScreen(navController) }
        composable("search") { ServiceSearchScreen(navController) }
        composable("booking") { Booking(navController, isLoggedIn,onLoginSuccess = onLoginSuccess) }
        composable("profile") { ProfileScreen(navController = navController) }

        // Account screen with login state
        composable("account") {
            Account(
                navController = navController,
                isLoggedIn = isLoggedIn,
                onLoginSuccess = onLoginSuccess,
                onLogout = onLogout
            )
        }

        // ✅ OTP screen
        composable("otp_screen") {
            val mobileNumber = navController.previousBackStackEntry
                ?.savedStateHandle?.get<String>("mobileNumber") ?: ""
            ExistingOtpScreen(
                mobileNumber = mobileNumber,
                onLoginSuccess = {
                    // Login happens only after Verify OTP
                    onLoginSuccess()
                    navController.popBackStack() // remove OTP screen from back stack
                },
                onBack = {
                    navController.popBackStack() // ✅ go back to previous screen on back
                }
            )
        }
    }

    // Bottom navigation visible only for selected screens
    if (currentRoute in screensWithBottomNav) {
        CustomBottomNavigationBar(navController)
    }
}
