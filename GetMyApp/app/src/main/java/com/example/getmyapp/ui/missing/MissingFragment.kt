package com.example.getmyapp.ui.missing

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getmyapp.R
import com.example.getmyapp.database.Pet
import com.example.getmyapp.utils.utils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MissingFragment : Fragment() {


    private lateinit var databasePets: DatabaseReference

    private lateinit var listOfPets: ArrayList<Pet>

    private lateinit var recyclerView: RecyclerView

    private lateinit var addMissingPetButton: FloatingActionButton

    private lateinit var filterButton: Button

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_missing, container, false)

        val species = resources.getStringArray(R.array.animal_species_array)
        val speciesSpinner = root.findViewById<Spinner>(R.id.speciesSpinner)
        if (speciesSpinner != null) {
            val adapter = ArrayAdapter(requireActivity(),
                    android.R.layout.simple_spinner_item, species)
            speciesSpinner.adapter = adapter
        }

        val colour = resources.getStringArray(R.array.colours_array)
        val colourSpinner = root.findViewById<Spinner>(R.id.colorSpinner)
        if (colourSpinner != null) {
            val adapter = ArrayAdapter(requireActivity(),
                    android.R.layout.simple_spinner_item, colour)
            colourSpinner.adapter = adapter
        }

        val region = resources.getStringArray(R.array.regions_array)
        val regionSpinner = root.findViewById<Spinner>(R.id.regionSpinner)
        if (regionSpinner != null) {
            val adapter = ArrayAdapter(requireActivity(),
                    android.R.layout.simple_spinner_item, region)
            regionSpinner.adapter = adapter
        }

        val addButton: FloatingActionButton = root.findViewById(R.id.addMissingPetButton)
        val user = utils.getLoginState(root.context)
        if (user != null)
            addButton.visibility = View.VISIBLE
        else
            addButton.visibility = View.INVISIBLE

        recyclerView = root.findViewById<RecyclerView>(R.id.missingPetsRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(root.context)

        databasePets = FirebaseDatabase.getInstance().getReference("Pets")

        databasePets.addValueEventListener(petListener)

        listOfPets = ArrayList<Pet>()

        addMissingPetButton = root.findViewById<FloatingActionButton>(R.id.addMissingPetButton)

        addMissingPetButton.setOnClickListener {
            val bundle = bundleOf("found" to false)
            findNavController().navigate(R.id.action_nav_missing_to_nav_add_report, bundle)
        }
        filterButton = root.findViewById<Button>(R.id.buttonFilterMissing)

        filterButton.setOnClickListener{
            val name = root.findViewById<SearchView>(R.id.nameSearchView).query.toString()
            val breed = root.findViewById<SearchView>(R.id.breedSearchView).query.toString()
            var species = speciesSpinner.selectedItem.toString()
            var colour = colourSpinner.selectedItem.toString()
            var region = regionSpinner.selectedItem.toString()
            val missingAdapter = recyclerView.adapter as MissingAdapter
            missingAdapter.removeFilter()
            if(species == getString(R.string.select_species))
                species = "default"
            if(colour == getString(R.string.select_colour))
                colour = "default"
            if(region == getString(R.string.select_region))
                region = "default"
            if(name.isEmpty() && breed.isEmpty() && species == "default" && colour == "default" && region == "default")
                missingAdapter.removeFilter()
            missingAdapter.filterList(name, species, colour, breed, region)
        }

        return root
    }


    // keeps track of all changes to pets DB
    val petListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val pets: HashMap<String, HashMap<String, String>> = dataSnapshot.getValue() as HashMap<String, HashMap<String, String>>

            listOfPets.clear()

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
                val found = value["found"].toString()
                if (found != null && found.compareTo("false") == 0) {
                    if (chipNo != null && name != null && species != null && breed != null && color != null
                        && age != null && gender != null && ownerId != null && region != null && lastSeen != null) {
                        val pet = Pet(
                            key, chipNo, name, species, breed, color, age, gender,
                            ownerId, region, lastSeen, found.toBoolean()
                        )
                        listOfPets.add(pet)
                    }
                }
            }
            recyclerView.adapter = MissingAdapter(listOfPets)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed
        }
    }
}