package com.example.getmyapp.database

data class Pet(var petId: String?,
               val chipNo: String,
               val name: String,
               val species: String,
               val breed: String,
               val color: String,
               val age: String,
               val gender: String,

               val ownerId: String,
               val region: String,
               val lastSeen: String,
               val found: Boolean
                )

