package com.example.beautyapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun Booking(navController: NavController) {
    MyAppointmentsScreen()
}

@Composable
fun MyAppointmentsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F3F8)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            Text(
                text = "No bookings found",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Looks like you haven't placed any booking yet!",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}