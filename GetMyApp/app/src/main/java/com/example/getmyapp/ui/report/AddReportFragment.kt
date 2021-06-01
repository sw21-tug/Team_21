package com.example.getmyapp.ui.report

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.getmyapp.R
import com.example.getmyapp.database.Pet
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class AddReportFragment: Fragment() {

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
    private lateinit var image: Uri

    private val selectImageResult = registerForActivityResult(ActivityResultContracts.GetContent()) {uri: Uri? ->
        if (uri != null) {
            image = uri
        }
    }

    private lateinit var databasePets: DatabaseReference
    private lateinit var storagePets: StorageReference

    private var found: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_add_pet, container, false)

        val species = resources.getStringArray(R.array.animal_species_array)
        val speciesSpinner = root.findViewById<Spinner>(R.id.createReportSpeciesSpinner)
        if (speciesSpinner != null) {
            val adapter = ArrayAdapter(requireActivity(),
                    android.R.layout.simple_spinner_item, species)
            speciesSpinner.adapter = adapter
        }

        databasePets = FirebaseDatabase.getInstance().getReference("Pets")
        storagePets = FirebaseStorage.getInstance().reference

        saveButton = root.findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener{ addPet() }

        val selectImageButton = root.findViewById<Button>(R.id.selectImageButton)
        selectImageButton.setOnClickListener { selectImageResult.launch("image/*") }

        found = arguments?.getBoolean("found") == true

        return root
    }

    private fun addPet() {
        if (!getInputData()) return

        val petId = databasePets.push().key

        val imageRef = storagePets.child("Pets/${petId}")
        val uploadTask = imageRef.putFile(image)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val imageLink = task.result
                // TODO: imageLink instead LastSeen
                val pet = Pet(petId, chipNumber, name, species, breed, color, age, gender, "123", region, imageLink.toString(), found)

                if (petId != null) {
                    databasePets.child(petId).setValue(pet)
                }
            }
        }





//        // TODO: UserID
//        val pet = Pet(petId, chipNumber, name, species, breed, color, age, gender, "123", region, lastSeen, found)
//
//        if (petId != null) {
//            databasePets.child(petId).setValue(pet)
//        }


        if (found) findNavController().navigate(R.id.action_nav_add_report_to_nav_found)
        else findNavController().navigate(R.id.action_nav_add_report_to_nav_missing)
    }

    private fun getInputData(): Boolean {
        val root = requireView()

        val nameEditText = root.findViewById<EditText>(R.id.createReportNameEditText)
        val speciesSpinner = root.findViewById<Spinner>(R.id.createReportSpeciesSpinner)
        val breedEditText = root.findViewById<EditText>(R.id.createReportBreedEditText)
        val colorEditText = root.findViewById<EditText>(R.id.createReportColourEditText)
        val ageEditText = root.findViewById<EditText>(R.id.createReportAgeEditText)
        val regionEditText = root.findViewById<EditText>(R.id.createReportRegionEditText)
        val genderEditText = root.findViewById<EditText>(R.id.createReportGenderEditText)
        val lastSeenEditText = root.findViewById<EditText>(R.id.createReportLastSeenEditText)
        val chipNumberEditText = root.findViewById<EditText>(R.id.createReportChipNumberEditText)

        var errorOccurred = false

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
            errorOccurred = true
        }
        if (species == activity?.resources?.getString(R.string.select_species)) {
            val spinnerView: TextView = speciesSpinner.selectedView as TextView
            spinnerView.error = activity?.resources?.getString(R.string.generic_error)
            errorOccurred = true
        }
        if (breed.isEmpty()) {
            breedEditText.error = activity?.resources?.getString(R.string.generic_error)
            errorOccurred = true
        }
        if (color.isEmpty()) {
            colorEditText.error = activity?.resources?.getString(R.string.generic_error)
            errorOccurred = true
        }
        if (age.isEmpty()) {
            ageEditText.error = activity?.resources?.getString(R.string.generic_error)
            errorOccurred = true
        }
        if (region.isEmpty()) {
            regionEditText.error = activity?.resources?.getString(R.string.generic_error)
            errorOccurred = true
        }
        if (gender.isEmpty()) {
            genderEditText.error = activity?.resources?.getString(R.string.generic_error)
            errorOccurred = true
        }
        if (lastSeen.isEmpty()) {
            lastSeenEditText.error = activity?.resources?.getString(R.string.generic_error)
            errorOccurred = true
        }
        if (chipNumber.isEmpty()) {
            chipNumberEditText.error = activity?.resources?.getString(R.string.generic_error)
            errorOccurred = true
        }

        if (errorOccurred) return false

        return true
    }
}