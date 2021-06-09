package com.example.getmyapp.ui.extendedreport

import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import com.example.getmyapp.database.User
import com.google.firebase.database.*
import java.net.URI
import java.util.*


class ExtendedReportFragment : Fragment() {

    private lateinit var databaseUsers: DatabaseReference

    private lateinit var user: User

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
        val genderTextView : TextView = root.findViewById(R.id.extendedReportGenderInfoTextView)
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


        val ownerID = arguments?.getString("ownerID");


        val phoneButton = root.findViewById<ImageButton>(R.id.imageButtonPhone)
        phoneButton.visibility = View.INVISIBLE

        val emailButton = root.findViewById<ImageButton>(R.id.imageButtonEmail)
        emailButton.visibility = View.INVISIBLE

        databaseUsers = FirebaseDatabase.getInstance().getReference("Users")

        databaseUsers.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot != null) {
                    val users: HashMap<String, HashMap<String, String>> =
                        dataSnapshot.getValue() as HashMap<String, HashMap<String, String>>

                    if (users != null) {

                        for ((key, value) in users) {
                            val userID = value["userId"]

                            if (userID != null && userID.equals(ownerID)) {
                                user = User(
                                    key,
                                    value["name"]!!,
                                    value["firstName"],
                                    value["lastName"],
                                    value["mailAddress"],
                                    value["phoneNumber"],
                                    value["hash"],
                                    value["salt"]
                                )

                                if (!user.phoneNumber.isNullOrEmpty())
                                    phoneButton.visibility = View.VISIBLE
                                if (!user.mailAddress.isNullOrEmpty())
                                    emailButton.visibility = View.VISIBLE
                                break
                            }
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })



        phoneButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + user.phoneNumber)
            requireActivity().startActivity(intent)
        }

        emailButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("mailto:" + user.mailAddress)
            requireActivity().startActivity(intent)
        }


        return root
    }
}