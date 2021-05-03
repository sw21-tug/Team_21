package com.example.getmyapp

import org.junit.Test
import com.example.getmyapp.ui.login.RegisterFragment
import com.example.getmyapp.ui.missing.MissingAdapter
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MissingAdapterTest {
    @Test
    fun getIteamCount_isCorrect() {
        val sampleData = arrayOf("test")
        val missingAdapter = MissingAdapter(arrayOf(sampleData))
        val iteamCount = missingAdapter.itemCount
        assertEquals(iteamCount, 1)
    }
}