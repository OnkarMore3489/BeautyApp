package com.example.beautyapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginButtonWithSheet(
    onLoginSuccess: () -> Unit,
) {
    var showSheet by remember { mutableStateOf(false) }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            scrimColor = Color.Black.copy(alpha = 0.5f)
        ) {
            var showOtpSheet by remember { mutableStateOf(false) }
            var mobileNumberForOtp by remember { mutableStateOf("") }

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
                    onLoginSuccess = {
                        showSheet = false
                        onLoginSuccess()
                    },
                    onBack = {
                        showOtpSheet = false
                    }
                )
            }
        }
    }
}
