package com.example.getmyapp

import com.example.getmyapp.database.Pet
import org.junit.Test
import com.example.getmyapp.ui.found.FoundAdapter
import org.junit.Assert.*


class FoundAdapterTest {
    @Test
    fun getItemCount_isCorrect() {
        val samplePet = Pet("id", "chipNo", "name", "species", "breed", "color", "age", "gender", "ownerId", "region", "lastSeen", true)
        var petList = ArrayList<Pet>()
        petList.add(samplePet)
        val foundAdapter = FoundAdapter(petList)
        val itemCount = foundAdapter.itemCount
        assertEquals(itemCount, 1)
    }
}