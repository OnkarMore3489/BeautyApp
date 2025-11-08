package com.example.beautyapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.beautyapp.navigation.AppNavHost
import com.example.beautyapp.database.AppDatabase
import com.example.beautyapp.login.ExistingOtpScreen
import com.example.beautyapp.login.FullScreenLoginPage
import com.example.beautyapp.splashscreen.SplashScreen
import com.example.beautyapp.datastore.UserPreferences
import com.example.beautyapp.presentation.NoInternetScreen
import com.example.beautyapp.ui.theme.BeautyAppTheme
import com.example.beautyapp.utils.NetworkUtils
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val list = AppSignatureHashHelper(this).appSignatures
        Log.d("TAG", "onCreate: $list")
//        Hash code
//        OS65xtUjRMg

        val db = AppDatabase.getInstance(applicationContext)
        val userPreferences = UserPreferences(applicationContext)

        setContent {
            BeautyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    var showSplash by remember { mutableStateOf(true) }
                    var isLoggedIn by remember { mutableStateOf(false) }
                    var showLoginScreen by remember { mutableStateOf(true) }
                    var showOtpScreen by remember { mutableStateOf(false) }
                    var mobileNumberForOtp by remember { mutableStateOf("") }
                    var pendingFirstTimeMobile by remember { mutableStateOf("") }
                    var isNewUser by remember { mutableStateOf(false) }
                    val isOnline by NetworkUtils.observeConnectivity(applicationContext)
                        .collectAsState(initial = true)

                    // 🔹 Read login state from DataStore
                    LaunchedEffect(Unit) {
                        val (loggedIn, _) = withContext(Dispatchers.IO) {
                            userPreferences.getLoginState()
                        }
                        isLoggedIn = loggedIn
                        showLoginScreen = !loggedIn
                    }

                    when {
                        !isOnline -> {
                            NoInternetScreen()
                        }
                        // 🔹 Splash screen
                        showSplash -> {
                            SplashScreen(onSplashFinished = { showSplash = false })
                        }

                        // 🔹 Login screen
                        showLoginScreen && !showOtpScreen -> {
                            FullScreenLoginPage(
                                onGetOtp = { mobile ->
                                    mobileNumberForOtp = mobile
                                    showOtpScreen = true
                                },
                                onSkip = {
                                    lifecycleScope.launch {
                                        userPreferences.saveLoginState(false, 0L)
                                    }
                                    isLoggedIn = false
                                    showLoginScreen = false
                                    showOtpScreen = false
                                }
                            )
                        }

                        // 🔹 OTP screen
                        showOtpScreen -> {
                            ExistingOtpScreen(
                                mobileNumber = mobileNumberForOtp,
                                onExistingUser = {
                                    lifecycleScope.launch(Dispatchers.IO) {
                                        val mobileLong = mobileNumberForOtp.toLongOrNull()
                                        if (mobileLong != null) {
                                            userPreferences.saveLoginState(true, mobileLong)
                                            withContext(Dispatchers.Main) {
                                                isLoggedIn = true
                                                showLoginScreen = false
                                                showOtpScreen = false
                                            }
                                        } else {
                                            withContext(Dispatchers.Main) {
                                                showOtpScreen = false
                                                showLoginScreen = true
                                            }
                                        }
                                        isNewUser = false
                                    }
                                },
                                onNewUser = {
                                    lifecycleScope.launch(Dispatchers.Main) {
                                        pendingFirstTimeMobile = mobileNumberForOtp
                                        showLoginScreen = false
                                        showOtpScreen = false
                                        isNewUser = true
                                    }
                                },
                                onBack = {
                                    showOtpScreen = false
                                    showLoginScreen = true
                                }
                            )
                        }

                        // 🔹 Main App (Home/Profile)
                        else -> {
                            AppNavHost(
                                navController = navController,
                                isLoggedIn = isLoggedIn,
                                pendingFirstTimeMobile = pendingFirstTimeMobile,
                                onLoginSuccess = {
                                    lifecycleScope.launch {
                                    }
                                    isLoggedIn = true
                                },
                                onLogout = {
                                        lifecycleScope.launch {
                                            userPreferences.saveLoginState(false, 0L)
                                        }
                                    isLoggedIn = false
                                },
                                isNewUser = isNewUser,
                            )
                        }
                    }
                }
            }
        }
    }
}