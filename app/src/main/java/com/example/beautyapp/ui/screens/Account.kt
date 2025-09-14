package com.example.beautyapp.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
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
import com.example.beautyapp.R

@Composable
fun Account(navController: NavController) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F6F8))
    ) {
        // ProfileCard (Fixed at the top)
        ProfileCard()

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
                    LogOut()
                    Spacer(modifier = Modifier.height(15.dp))
                    AppVersionSection()
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    }
}

//@Composable
//fun ProfileCard() {
//    Card(
//        shape = RoundedCornerShape(18.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 30.dp)
//            .padding(16.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            ProfileSection(Modifier.clickable { /* Handle Profile Click */ })
////            Spacer(modifier = Modifier.height(8.dp))
//        }
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable { /* Handle Elite Membership Click */ }
//                .background(
//                    Color.Black,
//                    shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
//                )
//                .padding(16.dp),
//            contentAlignment = Alignment.CenterStart
//        ) {
//            Text("Elite Membership", color = Color.Yellow, fontWeight = FontWeight.Bold)
//        }
//    }
//}

@Composable
fun ProfileCard() {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ProfileSection(Modifier.clickable { /* Handle Profile Click */ })
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* Handle Elite Membership Click */ }
                .background(
                    Color.Black,
                    shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left side: Hexagon Icon + "Elite Membership" text
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.Yellow, shape = RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "E",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Elite Membership",
                        color = Color.Yellow,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                // Right side: "Join Now >"
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Join Now",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Arrow",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun ProfileSection(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Profile Picture",
            modifier = Modifier.size(48.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text("Onkar More", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("+91 8999454017", color = Color.Gray, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.weight(1f))
        CoinDisplay(0)
    }
}

@Composable
fun CoinDisplay(coins: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(16.dp))
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
fun EarnOption(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle click */ }
            .padding(1.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
    Icon(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = "Profile Picture",
        modifier = Modifier.size(48.dp),
        tint = Color.Gray
    )
        Text(
            text = title,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Arrow",
                    modifier = Modifier
                    .padding(end = 10.dp) // Reduced right padding to move left
        )
    }
}

@Composable
fun LogOut() {
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
            Text("Logout", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
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

