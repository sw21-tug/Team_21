package com.example.getmyapp.ui.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.getmyapp.R
import com.example.getmyapp.database.Pet
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class addPetFragment: Fragment() {

    private lateinit var saveButton: Button

    private lateinit var name: String
    private lateinit var species: String
    private lateinit var breed: String
    private lateinit var color: String
    private lateinit var age: String
    private lateinit var region: String
    private lateinit var gender: String
    private lateinit var lastSeen: String
    private lateinit var chipNumber: String

    private lateinit var databasePets: DatabaseReference

    private var found: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_add_pet, container, false)

        val species = resources.getStringArray(R.array.animal_species_array)
        val speciesSpinner = root.findViewById<Spinner>(R.id.spinnerPetSpecies)
        if (speciesSpinner != null) {
            val adapter = ArrayAdapter(requireActivity(),
                    android.R.layout.simple_spinner_item, species)
            speciesSpinner.adapter = adapter
        }

        databasePets = FirebaseDatabase.getInstance().getReference("Pets")

        saveButton = root.findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener{ addPet() }

        found = arguments?.get("found").toString().toBoolean()

        return root
    }

    private fun addPet() {
        if (!getInputData()) return

        val petId = databasePets.push().key

        // TODO: UserID
        val pet = Pet(petId, chipNumber, name, species, breed, color, age, gender, "123", region, lastSeen, found)

        if (petId != null) {
            databasePets.child(petId).setValue(pet)
        }

        if (found) findNavController().navigate(R.id.action_addPetFragment_to_nav_found)
        else findNavController().navigate(R.id.action_addPetFragment_to_nav_missing)
    }

    fun getInputData(): Boolean {
        val root = requireView()

        val nameEditText = root.findViewById<EditText>(R.id.editTextTextPetName)
        val speciesSpinner = root.findViewById<Spinner>(R.id.spinnerPetSpecies)
        val breedEditText = root.findViewById<EditText>(R.id.editTextPetBreed)
        val colorEditText = root.findViewById<EditText>(R.id.editTextTextPetColor)
        val ageEditText = root.findViewById<EditText>(R.id.editTextTextPetAge)
        val regionEditText = root.findViewById<EditText>(R.id.editTextTextPetRegion)
        val genderEditText = root.findViewById<EditText>(R.id.editTextTextPetGender)
        val lastSeenEditText = root.findViewById<EditText>(R.id.editTextTextPetLastSeen)
        val chipNumberEditText = root.findViewById<EditText>(R.id.editTextTextPetChipNumber)

        var errorOccured = false

        name = nameEditText.text.toString()
        species = speciesSpinner.selectedItem.toString()
        breed = breedEditText.text.toString()
        color = colorEditText.text.toString()
        age = ageEditText.text.toString()
        region = regionEditText.text.toString()
        gender = genderEditText.text.toString()
        lastSeen = lastSeenEditText.text.toString()
        chipNumber = chipNumberEditText.text.toString()

        if (name.isEmpty()) {
            nameEditText.error = activity?.resources?.getString(R.string.generic_error)
            errorOccured = true
        }
        if (species.equals(activity?.resources?.getString(R.string.select_species))) {
            val spinnerView: TextView = speciesSpinner.selectedView as TextView
            spinnerView.error = activity?.resources?.getString(R.string.generic_error)
            errorOccured = true
        }
        if (breed.isEmpty()) {
            breedEditText.error = activity?.resources?.getString(R.string.generic_error)
            errorOccured = true
        }
        if (color.isEmpty()) {
            colorEditText.error = activity?.resources?.getString(R.string.generic_error)
            errorOccured = true
        }
        if (age.isEmpty()) {
            ageEditText.error = activity?.resources?.getString(R.string.generic_error)
            errorOccured = true
        }
        if (region.isEmpty()) {
            regionEditText.error = activity?.resources?.getString(R.string.generic_error)
            errorOccured = true
        }
        if (gender.isEmpty()) {
            genderEditText.error = activity?.resources?.getString(R.string.generic_error)
            errorOccured = true
        }
        if (lastSeen.isEmpty()) {
            lastSeenEditText.error = activity?.resources?.getString(R.string.generic_error)
            errorOccured = true
        }
        if (chipNumber.isEmpty()) {
            chipNumberEditText.error = activity?.resources?.getString(R.string.generic_error)
            errorOccured = true
        }

        if (errorOccured) return false

        return true
    }
}