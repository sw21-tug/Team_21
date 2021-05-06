package com.example.getmyapp

import com.example.getmyapp.ui.login.LoginFragment
import org.junit.Assert
import org.junit.Test

class LoginFragmentTest {
    @Test
    fun charsToByte_isCorrect() {
        val bytesCorrect: ByteArray = "Hallo".toByteArray()
        val charArray: CharArray = "Hallo".toCharArray()
        val bytes: ByteArray = LoginFragment().charsToBytes(charArray)

        Assert.assertArrayEquals(bytes, bytesCorrect)
    }
}