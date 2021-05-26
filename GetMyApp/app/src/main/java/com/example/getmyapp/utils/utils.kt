package com.example.getmyapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.getmyapp.database.User

object utils {
    private fun getSharedPref(applicationContext: Context): SharedPreferences {
        val mainKey = MasterKey.Builder(applicationContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val sharedPrefsFile = "user"
        return EncryptedSharedPreferences.create(
            applicationContext,
            sharedPrefsFile,
            mainKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun getLoginState(applicationContext: Context): User? {
        val sharedPref = getSharedPref(applicationContext)

        val userId = sharedPref.getString("user_id", null) ?: return null
        val username = sharedPref.getString("username", null) ?: return null
        val firstname = sharedPref.getString("firstname", null)
        val lastname = sharedPref.getString("lastname", null)
        val mail = sharedPref.getString("mail", null) ?: return null
        val phone = sharedPref.getString("phone", null)

        return User(userId, username, firstname, lastname, mail, phone, null, null)
    }

    fun saveLoginState(applicationContext: Context, user: User) {
        val sharedPref = getSharedPref(applicationContext)

        with (sharedPref.edit()) {
            putString("user_id", user.userId)
            putString("username", user.name)
            putString("firstname", user.firstName)
            putString("lastname", user.lastName)
            putString("mail", user.mailAddress)
            putString("phone", user.phoneNumber)
            apply()
        }
    }

    fun removeLoginState(applicationContext: Context) {
        val sharedPref = getSharedPref(applicationContext)

        with (sharedPref.edit()) {
            clear()
            apply()
        }
    }
}