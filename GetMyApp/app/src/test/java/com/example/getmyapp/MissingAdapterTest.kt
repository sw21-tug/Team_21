package com.example.getmyapp

import org.junit.Test
import com.example.getmyapp.ui.missing.MissingAdapter
import org.junit.Assert.*


class MissingAdapterTest {
    @Test
    fun getItemCount_isCorrect() {
        val sampleData = arrayOf("test")
        val missingAdapter = MissingAdapter(arrayOf(sampleData))
        val itemCount = missingAdapter.itemCount
        assertEquals(itemCount, 1)
    }
}