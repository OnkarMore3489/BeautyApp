package com.example.beautyapp.account

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.ui.platform.LocalContext
import com.example.beautyapp.R
import com.example.beautyapp.database.AppDatabase
import com.example.beautyapp.login.ExistingOtpScreen
import com.example.beautyapp.login.FullScreenLoginPage
import com.example.beautyapp.database.UserEntity
import com.example.beautyapp.datastore.UserPreferences
import com.example.beautyapp.presentation.LoadingScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun Account(
    navController: NavController,
    isLoggedIn: Boolean,
    onLoginSuccess: () -> Unit,
    onLogout: () -> Unit
) {
    val scrollState = rememberScrollState()
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(800)   // API or DB call
        loading = false
    }

    if (loading) {
        LoadingScreen()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F6F8))
    ) {
        // ProfileCard (Fixed at the top)
        ProfileCard(
            navController,
            isLoggedIn = isLoggedIn,
            onLoginSuccess = onLoginSuccess,
            onLogout = onLogout
        )

        // Scrollable content below WalletSection
        Box(
            modifier = Modifier
                .fillMaxSize()
//                .weight(1f) // Ensures the remaining content takes available space
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState) // Enables scrolling
                    .nestedScroll(rememberNestedScrollInteropConnection())
                    .padding(bottom = 56.dp) // Space for bottom navigation
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF8F6F8))
                        .padding(16.dp)
                ) {
                    WalletSection()
                    Spacer(modifier = Modifier.height(15.dp))
                    FeatureButtonsRow()
                    Spacer(modifier = Modifier.height(15.dp))
                    EarnWithUsSection_Updated()
                    Spacer(modifier = Modifier.height(15.dp))
                    EarnWithUsSection()
                    Spacer(modifier = Modifier.height(15.dp))
                    Read()
                    Spacer(modifier = Modifier.height(15.dp))
                    OtherInfo()
                    Spacer(modifier = Modifier.height(15.dp))
                    if (isLoggedIn) {
                        LogOut(onLogoutConfirmed = { onLogout() })
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    AppVersionSection()
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    }
}

@Composable
fun ProfileCard(
    navController: NavController,
    isLoggedIn: Boolean,
    onLoginSuccess: () -> Unit,
    onLogout: () -> Unit
) {
    // Remember any icon/vector resources safely
    val arrowIcon = remember { Icons.Default.ChevronRight }

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, start = 16.dp, end = 16.dp)
    ) {
        // Profile section (can be uncommented if needed)
        Column(modifier = Modifier.padding(16.dp)) {
            ProfileSection(
                navController,
                isLoggedIn = isLoggedIn,
                onLoginSuccess = onLoginSuccess,
                onLogout = onLogout,
                onNavigateToProfile = { mobileNumber ->
                    navController.navigate("profile_update/$mobileNumber/false")
                }
            )
        }

        // ✅ Moved Box inside Card with no recomposition issues
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    indication = rememberRipple(),
                    interactionSource = remember { MutableInteractionSource() }
                ) { /* Handle Elite Membership Click */ }
                .background(
                    Color.Black,
                    shape = RoundedCornerShape(
                        bottomStart = 12.dp,
                        bottomEnd = 12.dp
                    )
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 🔹 Left side: Icon + Text
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.Yellow, shape = RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "E",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Elite Membership",
                        color = Color.Yellow,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                // 🔹 Right side: Text + Chevron icon
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Join Now",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = arrowIcon, // ✅ Remembered icon
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSection(
    navController: NavController,
    isLoggedIn: Boolean,
    onLoginSuccess: () -> Unit,
    onLogout: () -> Unit,
    onNavigateToProfile: (Long) -> Unit
) {
    val context = LocalContext.current
    val userPrefs = remember { UserPreferences(context) }
    val db = remember { AppDatabase.Companion.getInstance(context) }

    // ✅ Holds current logged-in user
    var userState by remember { mutableStateOf<UserEntity?>(null) }

    // ✅ Refresh user info whenever login changes
    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            val (_, mobile) = userPrefs.getLoginState()
            if (mobile != null) {
                val user = db.userDao().getUserByMobile(mobile)
                userState = user
            }
        } else {
            userState = null
        }
    }

    val profilePainter = painterResource(id = R.drawable.ic_launcher_foreground)
    var showSheet by remember { mutableStateOf(false) }

    // 🔹 Main UI
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .then(
                if (isLoggedIn)
                    Modifier.clickable(
                        indication = rememberRipple(),
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        userState?.mobileNumber?.let { mobile ->
                            onNavigateToProfile(mobile)
                        }
                    }
                else Modifier
            )
            .padding(horizontal = 16.dp)
    ) {
        if (isLoggedIn) {
            Icon(
                painter = profilePainter,
                contentDescription = "Profile Picture",
                modifier = Modifier.size(48.dp),
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(12.dp))
        }

        Column(modifier = Modifier.weight(1f)) {
            if (isLoggedIn) {
                Text(
                    text = userState?.fullName ?: "Customer Name",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = userState?.mobileNumber?.let { "+91 $it" } ?: "",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            } else {
                Text("Your Profile", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(
                    "Log in or sign up to view your complete profile",
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        if (isLoggedIn) {
            CoinDisplay(0)
        } else {
            Button(
                onClick = { showSheet = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD32F2F),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                modifier = Modifier.height(32.dp)
            ) {
                Text("Login/Signup", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }

    // ✅ Bottom Sheet for login/signup
    if (showSheet) {
        val scope = rememberCoroutineScope()
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            scrimColor = Color.Black.copy(alpha = 0.5f)
        ) {
            var showOtpSheet by remember { mutableStateOf(false) }
            var mobileNumberForOtp by remember { mutableStateOf("") }
            var isNewUser by remember { mutableStateOf(false) }

            if (!showOtpSheet) {
                FullScreenLoginPage(
                    onGetOtp = { mobile ->
                        mobileNumberForOtp = mobile
                        showOtpSheet = true
                    },
                    onSkip = null
                )
            } else {
                ExistingOtpScreen(
                    mobileNumber = mobileNumberForOtp,
                    onExistingUser = {
                        scope.launch(Dispatchers.IO) {
                            val mobileLong = mobileNumberForOtp.toLongOrNull()
                            if (mobileLong != null) {
                                userPrefs.saveLoginState(true, mobileLong)
                                withContext(Dispatchers.Main) {
                                    showSheet = false
                                    onLoginSuccess() // triggers recomposition in parent
                                }
                            }
                        }
                        isNewUser = false
                    },
                    onNewUser = {
                        isNewUser = true
                        scope.launch {
                            navController.navigate("profile_update/$mobileNumberForOtp/$isNewUser")
                            showSheet = false
                        }
                    },
                    onBack = { showOtpSheet = false }
                )
            }
        }
    }
}

@Composable
fun CoinDisplay(coins: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Coins",
            modifier = Modifier.size(20.dp),
            tint = Color(0xFFFFD700)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text("$coins", fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Composable
fun WalletSection() {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp) // Set small height for WalletSection
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Wallet")
            Spacer(modifier = Modifier.width(8.dp))
            Text("YM Wallet: ₹0", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.ChevronRight, // Built-in right arrow icon
                contentDescription = "Arrow",
                modifier = Modifier
                        .padding(end = 10.dp) // Reduced right padding to move left
            )
        }
    }
}

@Composable
fun FeatureButtonsRow() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        FeatureButtonCard("My Bookings", R.drawable.ic_launcher_foreground)
        FeatureButtonCard("Addresses", R.drawable.ic_launcher_foreground)
        FeatureButtonCard("Help Center", R.drawable.ic_launcher_foreground)
    }
}

@Composable
fun FeatureButtonCard(title: String, iconRes: Int) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
//        modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(painter = painterResource(id = iconRes), contentDescription = title, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontSize = 14.sp)
        }
    }
}

@Composable
fun EarnWithUsSection_Updated() {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = "Vouchers",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            EarnOption("YM Gift Vouchers")
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun EarnWithUsSection() {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = "Earn With Us",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            EarnOption("Refer & Earn")
            Spacer(modifier = Modifier.height(5.dp))
            CustomDivider()
            EarnOption("Tie-Ups")
            Spacer(modifier = Modifier.height(5.dp))
            CustomDivider()
            EarnOption("Register as a Partner")
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun Read() {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = "Wanna Read Something?",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            EarnOption("Blog")
            Spacer(modifier = Modifier.height(5.dp))
            CustomDivider()
            EarnOption("Home Remedies")
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun OtherInfo() {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = "Other Information",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            EarnOption("Share the app")
            Spacer(modifier = Modifier.height(5.dp))
            CustomDivider()
            EarnOption("About Us")
            Spacer(modifier = Modifier.height(5.dp))
            CustomDivider()
            EarnOption("Privacy Policy")
            Spacer(modifier = Modifier.height(5.dp))
            CustomDivider()
            EarnOption("Notification Preferences")
            Spacer(modifier = Modifier.height(5.dp))
            CustomDivider()
            EarnOption("Contact Us")
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun EarnOption(
    title: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = rememberRipple(),
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
                .size(28.dp)
                .padding(end = 12.dp),
            tint = Color.Gray
        )

        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color.Gray
        )
    }
}



@Composable
fun LogOut(
    onLogoutConfirmed: () -> Unit
) {
    // ✅ Declare state correctly
    var showDialog by remember { mutableStateOf(false) }

    // 🔹 Logout Card
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable(
                indication = rememberRipple(), // 🔹 Ripple effect on click
                interactionSource = remember { MutableInteractionSource() } // 🔹 Tracks touch events
            ) {
                showDialog = true
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Logout Icon"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Logout",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Arrow",
                modifier = Modifier.padding(end = 10.dp)
            )
        }
    }

    // ✅ Show confirmation dialog safely
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm Logout") },
            text = { Text("Are you sure you want to logout?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        // ✅ Trigger logout safely after recomposition
                        onLogoutConfirmed()
                    }
                ) {
                    Text("Yes", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun AppVersionSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "BrandName",
            fontSize = 22.sp, // Slightly larger for better visibility
            fontWeight = FontWeight.ExtraBold, // Stronger weight for bold effect
            color = Color(0xFF3C3C3C), // Dark gray color matching the image
            letterSpacing = 1.sp // Slight spacing for better readability
        )
        Text(
            text = "version", // Version number
            fontSize = 12.sp, // Much smaller text
            fontWeight = FontWeight.Normal, // Thin but readable
            color = Color(0xFF9E9E9E) // Light gray color
        )
    }
}

@Composable
fun CustomDivider() {
    Divider(
        color = Color.Gray.copy(alpha = 0.2f), // Adjust alpha for more transparency
        thickness = 0.6.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}