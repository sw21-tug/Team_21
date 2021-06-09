package com.example.getmyapp.database

data class User(
    val userId: String,
    val name: String,
    val firstName: String?,
    val lastName: String?,
    val mailAddress: String?,
    val phoneNumber: String?,
    val hash: String?,
    val salt: String?
)