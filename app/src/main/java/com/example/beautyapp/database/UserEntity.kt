package com.example.beautyapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

// UserEntity.kt
@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val mobileNumber: Long,
    val fullName: String,
    val email: String,
    val dob: String,
    val gender: String,
    val maritalStatus: String
)