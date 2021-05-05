package com.example.getmyapp.ui.missing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.getmyapp.R
import com.example.getmyapp.database.Pet
import com.example.getmyapp.database.User
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MissingFragment : Fragment() {

    // TODO: delete VM?
    private lateinit var missingViewModel: MissingViewModel

    private lateinit var databasePets: DatabaseReference

    private lateinit var listOfPets: ArrayList<Pet>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        missingViewModel =
                ViewModelProvider(this).get(MissingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_missing, container, false)
        //val textView: TextView = root.findViewById(R.id.title_missing)
        //val textView2: TextView = root.findViewById(R.id.search_missing_title)
        //val textView3: TextView = root.findViewById(R.id.missing_breed)
        //val textView4: TextView = root.findViewById(R.id.missing_region)
        //val textView5: TextView = root.findViewById(R.id.missing_colour)
        //val textView6: TextView = root.findViewById(R.id.missing_species)
        val species = resources.getStringArray(R.array.animal_species)
        val spinner = root.findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(requireActivity(),
                    android.R.layout.simple_spinner_item, species)
            spinner.adapter = adapter
        }

        val colour = resources.getStringArray(R.array.colours)
        val spinner2 = root.findViewById<Spinner>(R.id.spinner2)
        if (spinner2 != null) {
            val adapter = ArrayAdapter(requireActivity(),
                    android.R.layout.simple_spinner_item, colour)
            spinner2.adapter = adapter
        }

        val region = resources.getStringArray(R.array.regions_missing)
        val spinner3 = root.findViewById<Spinner>(R.id.spinner3)
        if (spinner3 != null) {
            val adapter = ArrayAdapter(requireActivity(),
                    android.R.layout.simple_spinner_item, region)
            spinner3.adapter = adapter
        }


        val pet1 = Pet(null, "123", "name", "dog", "dalmatiner",
            "white", "5", "diverse", "012", "graz",
            "12.12.12")
        val pet2 = Pet(null, "1234", "waldi", "dog", "bulldog",
            "black", "5", "queer", "210", "meidling",
            "12.12.12")


        databasePets = FirebaseDatabase.getInstance().getReference("Pets")

        databasePets.addValueEventListener(petListener)

        listOfPets = ArrayList<Pet>()

        /*
        var petId = databasePets.push().key

        if (petId != null) {
            pet1.setId(petId)
            databasePets.child(petId).setValue(pet1)
        }

        petId = databasePets.push().key

        if (petId != null) {
            pet2.setId(petId)
            databasePets.child(petId).setValue(pet2)
        }
        */

        /*missingViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }

    // keeps track of all changes to pets DB
    val petListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val pets: HashMap<String, HashMap<String, String>> = dataSnapshot.getValue() as HashMap<String, HashMap<String, String>>

            for ((key, value) in pets) {
                val chipNo = value["chipNo"]
                val name = value["name"]
                val species = value["species"]
                val breed = value["breed"]
                val color = value["color"]
                val age = value["age"]
                val gender = value["gender"]
                val ownerId = value["ownerId"]
                val region = value["region"]
                val lastSeen = value["lastSeen"]
                if (chipNo != null && name != null && species != null && breed != null && color != null
                    && age != null && gender != null && ownerId != null && region != null && lastSeen != null) {
                    val pet: Pet = Pet(key, chipNo, name, species, breed, color, age, gender,
                    ownerId, region, lastSeen)
                    listOfPets.add(pet)
                }
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed
        }
    }
}