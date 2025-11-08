package com.example.beautyapp.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.beautyapp.account.Account
import com.example.beautyapp.booking.Booking
import com.example.beautyapp.home.CustomBottomNavigationBar
import com.example.beautyapp.login.ExistingOtpScreen
import com.example.beautyapp.login.FullScreenLoginPage
import com.example.beautyapp.home.HomeScreen
import com.example.beautyapp.location.LocationScreen
import com.example.beautyapp.home.man.MenHomeScreen
import com.example.beautyapp.offers.Offer
import com.example.beautyapp.profilescreen.ProfileScreen
import com.example.beautyapp.search.ServiceSearchScreen

@SuppressLint("SuspiciousIndentation")
@Composable
fun AppNavHost(
    navController: NavHostController,
    isLoggedIn: Boolean,
    pendingFirstTimeMobile: String,
    onLoginSuccess: () -> Unit,
    onLogout: () -> Unit,
    isNewUser: Boolean
) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route
    val screensWithBottomNav = listOf("home", "men","offer", "account", "booking")

    NavHost(
        navController = navController,
        startDestination = if (pendingFirstTimeMobile.isNotEmpty()) {
            "profile_update/$pendingFirstTimeMobile/$isNewUser"
        } else {
            "home"
        }
    ) {
        composable("login") {
            FullScreenLoginPage(
                onGetOtp = { mobile ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("mobileNumber", mobile)
                    navController.navigate("otp_screen")
                },
                onSkip = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("otp_screen") {
            val mobileNumber =
                navController.previousBackStackEntry?.savedStateHandle?.get<String>("mobileNumber") ?: ""
            ExistingOtpScreen(
                mobileNumber = mobileNumber,
                onExistingUser = {
                    onLoginSuccess()
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNewUser = {
                    Log.d("NavDebug", "Navigating to profile_update for new user: $mobileNumber")
                    navController.navigate("profile_update/${mobileNumber}/${isNewUser}") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable("location") { LocationScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("men") { MenHomeScreen(navController) }
        composable("offer") { Offer(navController) }
        composable("search") { ServiceSearchScreen(navController) }
        composable("booking") {
            Booking(navController, isLoggedIn, onLoginSuccess = onLoginSuccess)
        }
        composable(
            route = "profile_update/{mobileNumber}/{isNewUser}",
            arguments = listOf(
                navArgument("mobileNumber") { type = NavType.StringType },
                navArgument("isNewUser") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { backStackEntry ->
            val mobileNumber = backStackEntry.arguments?.getString("mobileNumber") ?: ""
            val isNewUser = backStackEntry.arguments?.getBoolean("isNewUser") ?: false

            Log.d("NavDebug", "ProfileScreen -> isNewUser = $isNewUser")

            ProfileScreen(
                navController = navController,
                mobileNumber = mobileNumber,
                isNewUser = isNewUser,
                onLoginSuccess = onLoginSuccess
            )
        }

        composable("account") {
            Account(
                navController = navController,
                isLoggedIn = isLoggedIn,
                onLoginSuccess = onLoginSuccess,
                onLogout = onLogout
            )
        }
    }

    if (currentRoute in screensWithBottomNav) {
        CustomBottomNavigationBar(navController)
    }
}


