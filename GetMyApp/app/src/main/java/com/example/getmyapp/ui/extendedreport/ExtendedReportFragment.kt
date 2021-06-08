package com.example.getmyapp.ui.extendedreport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.getmyapp.R
import com.example.getmyapp.ui.home.HomeViewModel
import com.google.firebase.storage.FirebaseStorage

class ExtendedReportFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_extendedreport, container, false)
        val nameTextView : TextView = root.findViewById(R.id.extendedReportPetNameInfoTextView)
        val speciesTextView : TextView = root.findViewById(R.id.extendedReportAnimalSpeciesInfoTextView)
        val breedTextView : TextView = root.findViewById(R.id.extendedReportBreedInfoTextView)
        val colorTextView : TextView = root.findViewById(R.id.extendedReportColourInfoTextView)
        val ageTextView : TextView = root.findViewById(R.id.extendedReportAgeInfoTextView)
        val regionTextView : TextView = root.findViewById(R.id.extendedReportRegionInfoTextView)
        val genderTextView : TextView = root.findViewById(R.id.extendedReportGenderTextView)
        val lastSeenTextView : TextView = root.findViewById(R.id.extendedReportLastSeenInfoTextView)
        val chipNrTextView : TextView = root.findViewById(R.id.extendedReportChipNumberInfoTextView)
        val petImageView : ImageView = root.findViewById(R.id.extendedReportImageView)

        nameTextView.text = arguments?.getString("name")
        speciesTextView.text = arguments?.getString("species")
        breedTextView.text = arguments?.getString("breed")
        colorTextView.text = arguments?.getString("color")
        ageTextView.text = arguments?.getString("age")
        regionTextView.text = arguments?.getString("region")
        genderTextView.text = arguments?.getString("gender")
        lastSeenTextView.text = arguments?.getString("lastSeen")
        chipNrTextView.text = arguments?.getString("chipNo")

        val petId = arguments?.getString("petId")
        val storagePets = FirebaseStorage.getInstance().reference
        val imageRef = storagePets.child("Pets/${petId}")

        Glide.with(root.context).load(imageRef).into(petImageView)

        return root
    }
}