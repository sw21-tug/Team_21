package com.example.getmyapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.getmyapp.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val imageButtonFoundPets: ImageButton = root.findViewById(R.id.imageButtonFoundPets)
        imageButtonFoundPets.setOnClickListener{
            findNavController().navigate(R.id.action_nav_home_to_nav_found)
        }
        val imageButtonLostPets: ImageButton =  root.findViewById(R.id.imageButtonMissingPets)
        imageButtonLostPets.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_missing)
        }


        return root
    }
}