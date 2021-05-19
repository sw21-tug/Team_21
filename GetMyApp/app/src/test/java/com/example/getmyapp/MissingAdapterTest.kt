package com.example.getmyapp

import com.example.getmyapp.database.Pet
import org.junit.Test
import com.example.getmyapp.ui.missing.MissingAdapter
import org.junit.Assert.*


class MissingAdapterTest {
    @Test
    fun getItemCount_isCorrect() {
        val samplePet = Pet("id", "chipNo", "name", "species", "breed", "color", "age", "gender", "ownerId", "region", "lastSeen", false)
        val petList = ArrayList<Pet>()
        petList.add(samplePet)
        val missingAdapter = MissingAdapter(petList)
        val itemCount = missingAdapter.itemCount
        assertEquals(itemCount, 1)
    }
}