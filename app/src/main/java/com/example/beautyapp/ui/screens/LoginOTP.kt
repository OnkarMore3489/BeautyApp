package com.example.beautyapp.ui.screens

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.http.UrlRequest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//@SuppressLint("DefaultLocale")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ExistingOtpScreen(
//    mobileNumber: String,
//    onLoginSuccess: () -> Unit,
//    onBack: () -> Unit
//) {
//    val otpValues = remember { mutableStateListOf("", "", "", "") }
//    val focusRequesters = List(4) { FocusRequester() }
//    val focusManager = LocalFocusManager.current
//    val allFilled = otpValues.all { it.isNotEmpty() }
//
//    var timer by remember { mutableStateOf(30) }
//    var otpError by remember { mutableStateOf("") }
//    var isOtpVerified by remember { mutableStateOf(false) }
//
//    val correctOtp = "1234"
//
//    // Countdown timer
//    LaunchedEffect(timer) {
//        if (timer > 0) {
//            delay(1000)
//            timer--
//        }
//    }
//
//    // Trigger login success after short delay
//    if (isOtpVerified) {
//        LaunchedEffect(Unit) {
//            delay(100)
//            onLoginSuccess()
//        }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        // Back Arrow
//        IconButton(
//            onClick = { onBack() },
//            modifier = Modifier
//                .padding(16.dp)
//                .align(Alignment.TopStart)
//        ) {
//            Icon(
//                imageVector = Icons.Default.ArrowBack,
//                contentDescription = "Back",
//                tint = Color.Black
//            )
//        }
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(horizontal = 24.dp)
//                .align(Alignment.TopCenter),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Spacer(modifier = Modifier.height(18.dp))
//
//            // App Title
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Text(
//                    text = "Beauty",
//                    fontSize = 32.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF007AFF)
//                )
//                Text(
//                    text = "Fox",
//                    fontSize = 32.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFFE91E63)
//                )
//            }
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            // OTP Title
//            Text(
//                text = "OTP Verification",
//                fontSize = 22.sp,
//                fontWeight = FontWeight.SemiBold,
//                color = Color.Black
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // OTP Subtitle
//            Text(
//                text = "We've sent an OTP to +91 ${mobileNumber.replaceRange(2, 8, "XXXXXX")}",
//                fontSize = 15.sp,
//                color = Color.Gray,
//                textAlign = TextAlign.Center
//            )
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            // OTP Boxes with proper backspace handling
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(12.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                otpValues.forEachIndexed { index, value ->
//                    var previousValue by remember { mutableStateOf(value) }
//
//                    Box(
//                        modifier = Modifier
//                            .size(60.dp)
//                            .focusRequester(focusRequesters[index])
//                            .background(Color.White, RoundedCornerShape(8.dp))
//                            .border(
//                                width = 1.dp,
//                                color = if (value.isNotEmpty()) Color(0xFFE91E63) else Color.LightGray,
//                                shape = RoundedCornerShape(8.dp)
//                            ),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        BasicTextField(
//                            value = value,
//                            onValueChange = { newValue ->
//                                if (newValue.length <= 1 && newValue.all(Char::isDigit)) {
//                                    otpValues[index] = newValue
//
//                                    // If user deletes the current field
//                                    if (previousValue.isNotEmpty() && newValue.isEmpty()) {
//                                        // Move focus back only
//                                        if (index > 0) {
//                                            focusRequesters[index - 1].requestFocus()
//                                        }
//                                    }
//
//                                    // Move forward on input
//                                    if (newValue.isNotEmpty() && index < otpValues.size - 1) {
//                                        focusRequesters[index + 1].requestFocus()
//                                    }
//
//                                    previousValue = newValue
//                                }
//                            },
//                            textStyle = TextStyle(
//                                fontSize = 22.sp,
//                                textAlign = TextAlign.Center,
//                                color = Color.Black
//                            ),
//                            singleLine = true,
//                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//                        )
//                    }
//                }
//            }
//
//
//            // OTP message
//            if (otpError.isNotEmpty()) {
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(
//                    text = otpError,
//                    color = if (otpError.contains("Successfully")) Color(0xFF4CAF50) else Color.Red,
//                    fontSize = 14.sp,
//                    textAlign = TextAlign.Center
//                )
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Timer and Resend
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Autorenew,
//                    contentDescription = "Auto Verify",
//                    modifier = Modifier.size(16.dp),
//                    tint = Color.Gray
//                )
//                Spacer(modifier = Modifier.width(6.dp))
//                Text("OTP Retry in ", color = Color.Gray, fontSize = 14.sp)
//                Text(
//                    String.format("00:%02d", timer),
//                    color = Color(0xFFE91E63),
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 14.sp
//                )
//                Text(" seconds", color = Color.Gray, fontSize = 14.sp)
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            if (timer == 0) {
//                TextButton(
//                    onClick = {
//                        otpValues.replaceAll { "" }
//                        otpError = ""
//                        isOtpVerified = false
//                        timer = 30
//                    }
//                ) {
//                    Text(
//                        text = "Resend OTP",
//                        color = Color(0xFFE91E63),
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Verify OTP button
//            Button(
//                onClick = {
//                    val enteredOtp = otpValues.joinToString("")
//                    if (enteredOtp == correctOtp) {
//                        otpError = "OTP Verified Successfully"
//                        isOtpVerified = true
//                    } else {
//                        otpError = "Invalid OTP. Please try again."
//                        isOtpVerified = false
//                    }
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp),
//                shape = RoundedCornerShape(10.dp),
//                enabled = allFilled,
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = if (allFilled) Color(0xFFE91E63) else Color.Gray
//                )
//            ) {
//                Text("Verify OTP", color = Color.White, fontSize = 16.sp)
//            }
//        }
//    }
//}

//@SuppressLint("DefaultLocale", "UnspecifiedRegisterReceiverFlag")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ExistingOtpScreen(
//    mobileNumber: String,
//    onLoginSuccess: () -> Unit,
//    onBack: () -> Unit
//) {
//    val otpValues = remember { mutableStateListOf("", "", "", "") }
//    val focusRequesters = List(4) { FocusRequester() }
//    val focusManager = LocalFocusManager.current
//    val allFilled = otpValues.all { it.isNotEmpty() }
//
//    var retrieverSession by remember { mutableStateOf(System.currentTimeMillis()) }
//    var timer by remember { mutableStateOf(30) }
//    var otpError by remember { mutableStateOf("") }
//    var isOtpVerified by remember { mutableStateOf(false) }
//
//    val correctOtp = "1234"
//    val context = LocalContext.current
//
//    // Countdown timer
//    LaunchedEffect(timer) {
//        if (timer > 0) {
//            delay(1000)
//            timer--
//        }
//    }
//
//    // Trigger login success after short delay
//    if (isOtpVerified) {
//        LaunchedEffect(Unit) {
//            delay(100)
//            onLoginSuccess()
//        }
//    }
//
//    // ✅ SMS Retriever API – Safe lifecycle inside Composable
//    DisposableEffect(retrieverSession) {
//        val client = SmsRetriever.getClient(context)
//        client.startSmsRetriever()
//            .addOnSuccessListener { Log.d("OTP", "SMS Retriever started") }
//            .addOnFailureListener { Log.e("OTP", "Failed to start SMS Retriever", it) }
//
//        val smsReceiver = object : BroadcastReceiver() {
//            override fun onReceive(ctx: Context?, intent: Intent?) {
//                if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action) {
//                    val extras = intent.extras
//                    val status = extras?.get(SmsRetriever.EXTRA_STATUS) as? Status
//
//                    if (status?.statusCode == CommonStatusCodes.SUCCESS) {
//                        val message = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as? String
//                        message?.let {
//                            val otpRegex = Regex("(\\d{4,6})")
//                            val match = otpRegex.find(it)
//                            val otp = match?.value
//
//                            if (!otp.isNullOrEmpty()) {
//                                val currentOtp = otpValues.joinToString("")
//                                if (otp != currentOtp) {
//                                    otp.forEachIndexed { index, c ->
//                                        if (index < otpValues.size) otpValues[index] = c.toString()
//                                    }
//
//                                    // ✅ Auto verify
//                                    CoroutineScope(Dispatchers.Main).launch {
//                                        delay(500)
//                                        val enteredOtp = otpValues.joinToString("")
//                                        if (enteredOtp == correctOtp) {
//                                            otpError = "OTP Verified Successfully"
//                                            isOtpVerified = true
//
//                                        } else {
//                                            otpError = "Invalid OTP. Please try again."
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        val filter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
//        ContextCompat.registerReceiver(
//            context,
//            smsReceiver,
//            filter,
//            ContextCompat.RECEIVER_EXPORTED
//        )
//
//        onDispose {
//            context.unregisterReceiver(smsReceiver)
//        }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        // Back Arrow
//        IconButton(
//            onClick = { onBack() },
//            modifier = Modifier
//                .padding(16.dp)
//                .align(Alignment.TopStart)
//        ) {
//            Icon(
//                imageVector = Icons.Default.ArrowBack,
//                contentDescription = "Back",
//                tint = Color.Black
//            )
//        }
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(horizontal = 24.dp)
//                .align(Alignment.TopCenter),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Spacer(modifier = Modifier.height(18.dp))
//
//            // App Title
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Text(
//                    text = "Beauty",
//                    fontSize = 32.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF007AFF)
//                )
//                Text(
//                    text = "Fox",
//                    fontSize = 32.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFFE91E63)
//                )
//            }
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            // OTP Title
//            Text(
//                text = "OTP Verification",
//                fontSize = 22.sp,
//                fontWeight = FontWeight.SemiBold,
//                color = Color.Black
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // OTP Subtitle
//            Text(
//                text = "We've sent an OTP to +91 ${mobileNumber.replaceRange(2, 8, "XXXXXX")}",
//                fontSize = 15.sp,
//                color = Color.Gray,
//                textAlign = TextAlign.Center
//            )
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            // OTP Boxes with proper backspace handling
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(12.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                otpValues.forEachIndexed { index, _ ->
//                    Box(
//                        modifier = Modifier
//                            .size(60.dp)
//                            .focusRequester(focusRequesters[index])
//                            .background(Color.White, RoundedCornerShape(8.dp))
//                            .border(
//                                width = 1.dp,
//                                color = if (otpValues[index].isNotEmpty()) Color(0xFFE91E63) else Color.LightGray,
//                                shape = RoundedCornerShape(8.dp)
//                            ),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        // Use the current value directly from otpValues (not a separate remember)
//                        val current = otpValues[index]
//                        BasicTextField(
//                            value = current,
//                            onValueChange = { newValue ->
//                                // keep only digits and max 1 char
//                                val filtered = newValue.filter { it.isDigit() }.take(1)
//
//                                // old value (before change)
//                                val oldValue = otpValues[index]
//
//                                // update
//                                otpValues[index] = filtered
//
//                                when {
//                                    // user cleared this field (backspace)
//                                    filtered.isEmpty() && oldValue.isNotEmpty() -> {
//                                        // move focus to previous field (but do not auto-clear prev)
//                                        if (index > 0) {
//                                            focusRequesters[index -1].requestFocus()
//                                        }
//                                    }
//
//                                    // user typed a digit -> move to next field
//                                    filtered.isNotEmpty() -> {
//                                        if (index < otpValues.size - 1) {
//                                            focusRequesters[index + 1].requestFocus()
//                                        } else {
//                                            // last field filled: you may want to hide keyboard here
//                                            // val keyboard = LocalSoftwareKeyboardController.current
//                                            // keyboard?.hide()
//                                        }
//                                    }
//                                }
//                            },
//                            textStyle = TextStyle(
//                                fontSize = 22.sp,
//                                textAlign = TextAlign.Center,
//                                color = Color.Black
//                            ),
//                            singleLine = true,
//                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .wrapContentHeight(Alignment.CenterVertically)
//                        )
//                    }
//                }
//            }
//
//
//            // OTP message
//            if (otpError.isNotEmpty()) {
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(
//                    text = otpError,
//                    color = if (otpError.contains("Successfully")) Color(0xFF4CAF50) else Color.Red,
//                    fontSize = 14.sp,
//                    textAlign = TextAlign.Center
//                )
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Timer and Resend
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Autorenew,
//                    contentDescription = "Auto Verify",
//                    modifier = Modifier.size(16.dp),
//                    tint = Color.Gray
//                )
//                Spacer(modifier = Modifier.width(6.dp))
//                Text("OTP Retry in ", color = Color.Gray, fontSize = 14.sp)
//                Text(
//                    String.format("00:%02d", timer),
//                    color = Color(0xFFE91E63),
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 14.sp
//                )
//                Text(" seconds", color = Color.Gray, fontSize = 14.sp)
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            if (timer == 0) {
//                TextButton(
//                    onClick = {
//                        otpValues.replaceAll { "" }
//                        otpError = ""
//                        isOtpVerified = false
//                        timer = 30
//                        retrieverSession = System.currentTimeMillis()
//                    }
//                ) {
//                    Text(
//                        text = "Resend OTP",
//                        color = Color(0xFFE91E63),
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Verify OTP button
//            Button(
//                onClick = {
//                    val enteredOtp = otpValues.joinToString("")
//                    if (enteredOtp == correctOtp) {
//                        otpError = "OTP Verified Successfully"
//                        isOtpVerified = true
//                    } else {
//                        otpError = "Invalid OTP. Please try again."
//                        isOtpVerified = false
//                    }
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp),
//                shape = RoundedCornerShape(10.dp),
//                enabled = allFilled,
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = if (allFilled) Color(0xFFE91E63) else Color.Gray
//                )
//            ) {
//                Text("Verify OTP", color = Color.White, fontSize = 16.sp)
//            }
//        }
//    }
//}

@SuppressLint("DefaultLocale", "UnspecifiedRegisterReceiverFlag")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExistingOtpScreen(
    mobileNumber: String,
    onLoginSuccess: () -> Unit,
    onBack: () -> Unit
) {
    val otpValues = remember { mutableStateListOf("", "", "", "") }
    val focusRequesters = List(4) { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val allFilled = otpValues.all { it.isNotEmpty() }

    var retrieverSession by remember { mutableStateOf(System.currentTimeMillis()) }
    var timer by remember { mutableStateOf(30) }
    var otpError by remember { mutableStateOf("") }
    var isOtpVerified by remember { mutableStateOf(false) }

    val correctOtp = "1234"
    val context = LocalContext.current

    // Countdown timer
    LaunchedEffect(timer) {
        if (timer > 0) {
            delay(1000)
            timer--
        }
    }

    // Auto trigger login after OTP verified
    if (isOtpVerified) {
        LaunchedEffect(Unit) {
            delay(100)
            onLoginSuccess()
        }
    }

    // ✅ Separate SMS Retriever logic
    DisposableEffect(retrieverSession) {
        val otpReceiver = OtpReceiver { otp ->
            otp.forEachIndexed { index, c ->
                if (index < otpValues.size) otpValues[index] = c.toString()
            }

            CoroutineScope(Dispatchers.Main).launch {
                delay(20)
                val enteredOtp = otpValues.joinToString("")
                if (enteredOtp == correctOtp) {
                    otpError = "OTP Verified Successfully"
                    isOtpVerified = true
                } else {
                    otpError = "Invalid OTP. Please try again."
                }
            }
        }

        otpReceiver.register(context)

        onDispose {
            otpReceiver.unregister(context)
        }
    }

    // ✅ UI section (same as before)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Back Arrow
        IconButton(
            onClick = { onBack() },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(18.dp))

            // App Title
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

            // OTP Title
            Text(
                text = "OTP Verification",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle
            Text(
                text = "We've sent an OTP to +91 ${mobileNumber.replaceRange(2, 8, "XXXXXX")}",
                fontSize = 15.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // OTP Boxes
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                otpValues.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .focusRequester(focusRequesters[index])
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .border(
                                width = 1.dp,
                                color = if (otpValues[index].isNotEmpty()) Color(0xFFE91E63) else Color.LightGray,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        val current = otpValues[index]
                        BasicTextField(
                            value = current,
                            onValueChange = { newValue ->
                                val filtered = newValue.filter { it.isDigit() }.take(1)
                                val oldValue = otpValues[index]
                                otpValues[index] = filtered

                                when {
                                    // Backspace
                                    filtered.isEmpty() && oldValue.isNotEmpty() -> {
                                        if (index > 0) focusRequesters[index - 1].requestFocus()
                                    }
                                    // Move forward
                                    filtered.isNotEmpty() -> {
                                        if (index < otpValues.size - 1)
                                            focusRequesters[index + 1].requestFocus()
                                    }
                                }
                            },
                            textStyle = TextStyle(
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentHeight(Alignment.CenterVertically)
                        )
                    }
                }
            }

            // OTP Message
            if (otpError.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = otpError,
                    color = if (otpError.contains("Successfully")) Color(0xFF4CAF50) else Color.Red,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Timer
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Autorenew,
                    contentDescription = "Auto Verify",
                    modifier = Modifier.size(16.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("OTP Retry in ", color = Color.Gray, fontSize = 14.sp)
                Text(
                    String.format("00:%02d", timer),
                    color = Color(0xFFE91E63),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(" seconds", color = Color.Gray, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Resend OTP
            if (timer == 0) {
                TextButton(
                    onClick = {
                        otpValues.replaceAll { "" }
                        otpError = ""
                        isOtpVerified = false
                        timer = 30
                        retrieverSession = System.currentTimeMillis()
                    }
                ) {
                    Text(
                        text = "Resend OTP",
                        color = Color(0xFFE91E63),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Verify OTP Button
            Button(
                onClick = {
                    val enteredOtp = otpValues.joinToString("")
                    if (enteredOtp == correctOtp) {
                        otpError = "OTP Verified Successfully"
                        isOtpVerified = true
                    } else {
                        otpError = "Invalid OTP. Please try again."
                        isOtpVerified = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                enabled = allFilled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (allFilled) Color(0xFFE91E63) else Color.Gray
                )
            ) {
                Text("Verify OTP", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}