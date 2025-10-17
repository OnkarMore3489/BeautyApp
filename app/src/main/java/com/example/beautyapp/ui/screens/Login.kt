package com.example.beautyapp.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenLoginPage(
    onGetOtp: (String) -> Unit,   // callback to navigate to OTP with mobile number
    onSkip: (() -> Unit)? = null  // optional skip
) {
    var mobileNumber by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(true) }

    val isMobileValid = mobileNumber.length == 10

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(18.dp))

            // Title
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Beauty",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF007AFF)
                )
                Text(
                    text = "Fox",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE91E63)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Mobile number input
            Text("Enter Mobile Number", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF8F8F8), RoundedCornerShape(10.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text("+91", fontSize = 16.sp, color = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    value = mobileNumber,
                    onValueChange = {
                        if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                            mobileNumber = it
                        }
                    },
                    placeholder = { Text("Mobile Number") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // WhatsApp checkbox
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF25D366))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Get order updates on WhatsApp", fontSize = 14.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Get OTP button (calls onGetOtp callback)
            Button(
                onClick = { onGetOtp(mobileNumber) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isMobileValid) Color(0xFFE91E63) else Color.Gray
                ),
                enabled = isMobileValid
            ) {
                Text("Get OTP", color = Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Skip button
            if (onSkip != null) {
                TextButton(onClick = { onSkip() }) {
                    Text("Skip", color = Color.Gray, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Terms & Conditions
            Text(
                text = buildAnnotatedString {
                    append("By continuing, you agree to our ")
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append("Terms & Conditions")
                    }
                    append(" and ")
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append("Privacy Policy")
                    }
                    append(".")
                },
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp
            )
        }
    }
}






