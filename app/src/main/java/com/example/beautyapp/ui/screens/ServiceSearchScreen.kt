package com.example.beautyapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ServiceSearchScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") } // State for search input

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 🔹 Search Bar with Back Button
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search for services") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.clickable { navController.popBackStack() }
                )
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )

        // 🔹 Popular Searches Title
        Text(
            text = "Popular Searches",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 20.dp, bottom = 12.dp)
        )

        // 🔹 LazyVerticalGrid for Popular Searches (Styled as Chips)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp), // Auto adjust grid
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val services = listOf(
                "Waxing", "Facial", "Clean-Up", "De-Tan", "Bleach",
                "Mani-Pedi", "Threading", "Face Mask", "Scrub",
                "Bikini Wax", "Manicure", "Pedicure", "LED Facial", "Spa",
                "Body Polishing", "Head Massage"
            )

            items(services) { service ->
                ServiceChip(service) { selectedService ->
                    searchQuery = selectedService // Update search query when clicked
                }
            }
        }
    }
}

// ✅ Updated ServiceChip to Handle Click
@Composable
fun ServiceChip(service: String, onServiceClick: (String) -> Unit) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        border = ButtonDefaults.outlinedButtonBorder,
        shadowElevation = 2.dp,
        modifier = Modifier.padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { onServiceClick(service) } // Triggers search
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = service,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}



