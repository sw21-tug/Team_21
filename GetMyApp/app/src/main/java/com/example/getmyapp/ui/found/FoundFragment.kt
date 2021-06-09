package com.example.getmyapp.ui.found

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.SearchView
import android.widget.Spinner
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


class FoundFragment : Fragment() {

    private lateinit var databasePets: DatabaseReference

    private lateinit var listOfPets: ArrayList<Pet>

    private lateinit var  recyclerView: RecyclerView

    private lateinit var addFoundPetButton: FloatingActionButton

    private lateinit var filterButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_found, container, false)

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

        addFoundPetButton = root.findViewById(R.id.addFoundPetButton)
        val user = utils.getLoginState(root.context)
        if (user != null)
            addFoundPetButton.visibility = View.VISIBLE
        else
            addFoundPetButton.visibility = View.INVISIBLE

        recyclerView = root.findViewById(R.id.foundPetsRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(root.context)

        databasePets = FirebaseDatabase.getInstance().getReference("Pets")

        databasePets.addValueEventListener(petListener)

        listOfPets = ArrayList()

        addFoundPetButton.setOnClickListener {

            val bundle = bundleOf("found" to true)
            findNavController().navigate(R.id.action_nav_found_to_nav_add_report, bundle)
        }

        filterButton = root.findViewById<Button>(R.id.buttonFilterFound)

        filterButton.setOnClickListener{
            val name = root.findViewById<SearchView>(R.id.nameSearchView).query.toString()
            val breed = root.findViewById<SearchView>(R.id.breedSearchView).query.toString()
            var species = speciesSpinner.selectedItem.toString()
            var colour = colourSpinner.selectedItem.toString()
            var region = regionSpinner.selectedItem.toString()
            val foundAdapter = recyclerView.adapter as FoundAdapter
            foundAdapter.removeFilter()
            if(species == getString(R.string.select_species))
                species = "default"
            if(colour == getString(R.string.select_colour))
                colour = "default"
            if(region == getString(R.string.select_region))
                region = "default"
            if(name.isEmpty() && breed.isEmpty() && species == "default" && colour == "default" && region == "default")
                foundAdapter.removeFilter()
            foundAdapter.filterList(name, species, colour, breed, region)
        }

        return root
    }

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
                if ( found != null && found.compareTo("true") == 0) {
                    if (chipNo != null && name != null && species != null && breed != null && color != null
                        && age != null && gender != null && ownerId != null && region != null && lastSeen != null
                    ) {
                        val pet = Pet(
                            key, chipNo, name, species, breed, color, age, gender,
                            ownerId, region, lastSeen, found.toBoolean()
                        )
                        listOfPets.add(pet)
                    }
                }
            }
            recyclerView.adapter = FoundAdapter(listOfPets)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed
        }
    }
}