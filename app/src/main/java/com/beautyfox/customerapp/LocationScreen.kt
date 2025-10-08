package com.beautyfox.customerapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LocationScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 🔹 Top Bar with Back Button & Title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Select Location",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Search Bar
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search for area, street name") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Add Address & Use Current Location (Single Card but Separate Clickable Areas)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(Color.White) // Background is White
        ) {
            Column {
                // 🔹 Add Address Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /* Handle Add Address Click */ }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "+ Add address",
                        color = Color(0xFFB00020), // Red color
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Filled.ArrowForwardIos,
                        contentDescription = "Next",
                        tint = Color(0xFFB00020)
                    )
                }

                Divider(color = Color.LightGray, thickness = 1.dp)

                // 🔹 Use Current Location Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /* Handle Use Current Location Click */ }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.MyLocation,
                        contentDescription = "Location",
                        tint = Color(0xFFB00020),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Use your current location",
                        color = Color(0xFFB00020), // Red color
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Saved Addresses Header with Lines
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                color = Color.LightGray,
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
            )

            Text(
                text = "SAVED ADDRESSES",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Divider(
                color = Color.LightGray,
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
            )
        }

        // 🔹 Saved Address Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* Handle address selection */ },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Dhanori", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(
                    "1, Dhanori-Lohegaon Rd, Parande Nagar, Dhanori, Pune, Maharashtra 411015, India",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}