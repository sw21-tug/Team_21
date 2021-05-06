package com.example.getmyapp

import org.junit.Test
import com.example.getmyapp.ui.found.FoundAdapter
import org.junit.Assert.*


class FoundAdapterTest {
    @Test
    fun getItemCount_isCorrect() {
        val sampleData = arrayOf("test")
        val foundAdapter = FoundAdapter(arrayOf(sampleData))
        val itemCount = foundAdapter.itemCount
        assertEquals(itemCount, 1)
    }
}