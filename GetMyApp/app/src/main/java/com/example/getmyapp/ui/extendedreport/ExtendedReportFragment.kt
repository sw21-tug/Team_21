package com.example.getmyapp.ui.extendedreport

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.getmyapp.R
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
        val genderTextView : TextView = root.findViewById(R.id.extendedReportGenderTextView)
        val lastSeenTextView : TextView = root.findViewById(R.id.extendedReportLastSeenInfoTextView)
        val chipNrTextView : TextView = root.findViewById(R.id.extendedReportChipNumberInfoTextView)
        nameTextView.text = arguments?.getString("name")
        speciesTextView.text = arguments?.getString("species")
        breedTextView.text = arguments?.getString("breed")
        colorTextView.text = arguments?.getString("color")
        ageTextView.text = arguments?.getString("age")
        regionTextView.text = arguments?.getString("region")
        genderTextView.text = arguments?.getString("gender")
        lastSeenTextView.text = arguments?.getString("lastSeen")
        chipNrTextView.text = arguments?.getString("chipNo")

        val ownerID = arguments?.getString("ownerID");

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
                                break
                            }
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        val phoneButton = root.findViewById<ImageButton>(R.id.imageButtonPhone)
        phoneButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + user!!.phoneNumber)
            requireActivity().startActivity(intent)
        }

        val emailButton = root.findViewById<ImageButton>(R.id.imageButtonEmail)
        emailButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("mailto:" + user!!.mailAddress)
            requireActivity().startActivity(intent)
        }


        return root
    }
}