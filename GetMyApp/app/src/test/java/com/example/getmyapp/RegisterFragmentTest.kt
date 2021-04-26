package com.example.getmyapp

import org.junit.Test
import com.example.getmyapp.ui.login.RegisterFragment
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RegisterFragmentTest {
    @Test
    fun charsToByte_isCorrect() {
        val bytesCorrect: ByteArray = "Hallo".toByteArray()
        val charArray: CharArray = "Hallo".toCharArray()
        val bytes: ByteArray = RegisterFragment().charsToBytes(charArray)

        assertArrayEquals(bytes, bytesCorrect)
    }
}