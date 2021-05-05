package com.example.getmyapp.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val userId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?,
    @NonNull @ColumnInfo(name = "mail_address") val mailAddress: String?,
    @ColumnInfo(name = "phone_number") val phoneNumber: String?,
    @NonNull @ColumnInfo(name = "hash") val hash: String?,
    @NonNull @ColumnInfo(name = "salt") val salt: String?
)