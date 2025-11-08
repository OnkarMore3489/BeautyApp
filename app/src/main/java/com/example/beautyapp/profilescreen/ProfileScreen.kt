package com.example.beautyapp.profilescreen

import android.app.DatePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import com.example.beautyapp.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.beautyapp.database.AppDatabase
import com.example.beautyapp.database.UserEntity
import com.example.beautyapp.datastore.UserPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    mobileNumber: String,
    isNewUser: Boolean = false,
    onLoginSuccess: () -> Unit
) {
    Log.d("ProfileScreen", "isNewUser=$isNewUser, mobile=$mobileNumber")

    var fullName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var dob by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("Female") } // empty until selected
    var maritalStatus by rememberSaveable { mutableStateOf("Single") }
    var originalFullName by remember { mutableStateOf("") }

    val context = LocalContext.current
    val userPrefs = remember { UserPreferences(context) }
    val db = AppDatabase.getInstance(context)

    // 🔹 Fetch user info if existing user
    LaunchedEffect(mobileNumber) {
        if (!isNewUser) {
            val mobileLong = mobileNumber.toLongOrNull()
            if (mobileLong != null) {
                val user = withContext(Dispatchers.IO) {
                    db.userDao().getUserByMobile(mobileLong)
                }
                user?.let {
                    fullName = it.fullName
                    email = it.email ?: ""
                    dob = it.dob ?: ""
                    gender = it.gender ?: ""
                    maritalStatus = it.maritalStatus ?: ""
                    originalFullName = it.fullName
                }
            }
        }
    }

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                dob = "$dayOfMonth/${month + 1}/$year"
            },
            2000, 0, 1
        )
    }

    // 🔹 Enable button only if required fields are filled

    val isContinueEnabled = if (isNewUser) {
        fullName.trim().isNotEmpty() && dob.isNotEmpty() && gender.isNotEmpty()
    } else {
        // For existing users, always enabled
        true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isNewUser) "Let Us Know You Better" else "Your Profile",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isNewUser) {
                Box(
                    modifier = Modifier.size(120.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF2F2F2))
                    )
                    IconButton(
                        onClick = { /* handle image picker */ },
                        modifier = Modifier
                            .size(30.dp)
                            .background(Color(0xFFD81B60), CircleShape)
                            .align(Alignment.BottomEnd)
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            // 🔹 Full Name
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Full Name *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFD81B60),
                    unfocusedBorderColor = Color.Gray
                ),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                placeholder = { Text("Enter email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFD81B60),
                    unfocusedBorderColor = Color.Gray
                ),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Date of Birth
            OutlinedTextField(
                value = dob,
                onValueChange = {},
                label = { Text("Date of Birth *") },
                placeholder = { Text("Select Date of Birth") },
                trailingIcon = {
                    IconButton(onClick = { datePickerDialog.show() }) {
                        Icon(Icons.Default.CalendarToday, contentDescription = "Calendar")
                    }
                },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Marital Status
            Text("Marital Status", fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.align(Alignment.Start)) {
                GenderButton("Single", maritalStatus == "Single") { maritalStatus = "Single" }
                GenderButton("Married", maritalStatus == "Married") { maritalStatus = "Married" }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Gender
            Text("Gender *", fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.align(Alignment.Start)) {
                GenderButton("Female", gender == "Female") { gender = "Female" }
                GenderButton("Male", gender == "Male") { gender = "Male" }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // 🔹 Save Button
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        val mobile = mobileNumber.toLongOrNull()
                        if (mobile != null) {
                            val existingUser = db.userDao().getUserByMobile(mobile)
                            if (existingUser != null) {
                                val updatedUser = existingUser.copy(
                                    fullName = fullName,
                                    email = email,
                                    dob = dob,
                                    gender = gender,
                                    maritalStatus = maritalStatus
                                )
                                db.userDao().updateUser(updatedUser)
                            } else {
                                val newUser = UserEntity(
                                    mobileNumber = mobile,
                                    fullName = fullName,
                                    email = email,
                                    dob = dob,
                                    gender = gender,
                                    maritalStatus = maritalStatus
                                )
                                db.userDao().insertUser(newUser)
                            }

                            userPrefs.saveLoginState(true, mobile)

                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    if (isNewUser) "Profile Created!" else "Profile Updated!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.previousBackStackEntry?.savedStateHandle?.set("profileUpdated", true)
                                onLoginSuccess()
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Invalid mobile number", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                enabled = isContinueEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isContinueEnabled) Color(0xFFD81B60) else Color(0xFFEF9A9A)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text(
                    text = if (isNewUser) "Continue" else "Update Profile",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}




@Composable
fun GenderButton(text: String, selected: Boolean, onClick: () -> Unit) {
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) Color(0xFFFFEBEE) else Color.White,
        label = ""
    )
    val textColor by animateColorAsState(
        targetValue = if (selected) Color(0xFFD81B60) else Color.Gray,
        label = ""
    )

    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        border = BorderStroke(1.dp, if (selected) Color(0xFFD81B60) else Color.Gray),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}