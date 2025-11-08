package com.example.beautyapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

// UserDao.kt
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user_table WHERE mobileNumber = :mobileNumber LIMIT 1")
    suspend fun getUserByMobile(mobileNumber: Long): UserEntity?

    @Update
    suspend fun updateUser(user: UserEntity)
}
