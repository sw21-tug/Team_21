package com.example.getmyapp

import org.junit.Test
import com.example.getmyapp.ui.login.RegisterFragment
import org.junit.Assert.*

class RegisterFragmentTest {
    @Test
    fun charsToByte_isCorrect() {
        val bytesCorrect: ByteArray = "Hallo".toByteArray()
        val charArray: CharArray = "Hallo".toCharArray()
        val bytes: ByteArray = RegisterFragment().charsToBytes(charArray)

        assertArrayEquals(bytes, bytesCorrect)
    }
}