package com.beautyfox.customerapp.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.beautyfox.customerapp.account.Account
import com.beautyfox.customerapp.booking.Booking
import com.beautyfox.customerapp.home.CustomBottomNavigationBar
import com.beautyfox.customerapp.home.HomeScreen
import com.beautyfox.customerapp.LocationScreen
import com.beautyfox.customerapp.home.man.MenHomeScreen
import com.beautyfox.customerapp.offers.OffersScreen
import com.beautyfox.customerapp.search.ServiceSearchScreen

//@Composable
//fun AppNavHost(navController: NavHostController) {
//    NavHost(navController = navController, startDestination = "home") {
//        composable("home") { HomeScreen(navController) }
//        composable("location") { LocationScreen(navController) }
//        composable("search") { ServiceSearchScreen(navController) }
//        composable("men") { MenHomeScreen(navController) }
//    }
//}

@SuppressLint("SuspiciousIndentation")
@Composable
fun AppNavHost(navController: NavHostController) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()

    val screensWithBottomNav = listOf("home", "men","account","booking")

    val currentRoute = currentBackStackEntry.value?.destination?.route

        NavHost(navController, startDestination = "home") {
            // Screens without Bottom Navigation
            composable("location") { LocationScreen(navController) }
            composable("search") { ServiceSearchScreen(navController) }

            // Screens with Bottom Navigation
            composable("home") { HomeScreen(navController) }
            composable("men") { MenHomeScreen(navController) }
            composable("Offers") { OffersScreen(navController) }
            composable("account") { Account(navController) }
            composable("booking") { Booking(navController) }
        }

        // ✅ Show Bottom Navigation only for specific screens
        if (currentRoute in screensWithBottomNav) {
            CustomBottomNavigationBar(navController)
        }
}



//@Composable
//fun AppNavigator() {
//    val navController = rememberNavController()
//    val currentBackStackEntry = navController.currentBackStackEntryAsState()
//
//    // ✅ Define screens where BottomNavigationBar should be visible
//    val screensWithBottomNav = listOf("women", "men", "booking", "account")
//
//    Scaffold(
//        bottomBar = {
//            val currentRoute = currentBackStackEntry.value?.destination?.route
//            if (currentRoute in screensWithBottomNav) {
//                CustomBottomNavigationBar(navController)
//            }
//        }
//    ) { paddingValues ->
//        Box(modifier = Modifier.padding(paddingValues)) {
//            NavHost(navController, startDestination = "login") { // ✅ Start at login screen
//                // ✅ Screens without BottomNavigationBar
//                composable("location") { LocationScreen(navController) }
//                composable("search") { ServiceSearchScreen(navController) }
//
//                // ✅ Screens with BottomNavigationBar
//                composable("home") { HomeScreen(navController) }
//                composable("men") { MenHomeScreen(navController) }
////                composable("booking") { BookingScreen(navController) }
////                composable("account") { AccountScreen(navController) }
//            }
//        }
//    }
//}

