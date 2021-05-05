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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getmyapp.R


class MissingFragment : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_missing, container, false)

        val species = resources.getStringArray(R.array.animal_species)
        val speciesSpinner = root.findViewById<Spinner>(R.id.speciesSpinner)
        if (speciesSpinner != null) {
            val adapter = ArrayAdapter(requireActivity(),
                    android.R.layout.simple_spinner_item, species)
            speciesSpinner.adapter = adapter
        }

        val colour = resources.getStringArray(R.array.colours)
        val colourSpinner = root.findViewById<Spinner>(R.id.colorSpinner)
        if (colourSpinner != null) {
            val adapter = ArrayAdapter(requireActivity(),
                    android.R.layout.simple_spinner_item, colour)
            colourSpinner.adapter = adapter
        }

        val region = resources.getStringArray(R.array.regions_missing)
        val regionSpinner = root.findViewById<Spinner>(R.id.regionSpinner)
        if (regionSpinner != null) {
            val adapter = ArrayAdapter(requireActivity(),
                    android.R.layout.simple_spinner_item, region)
            regionSpinner.adapter = adapter
        }

        val recyclerView = root.findViewById<RecyclerView>(R.id.missingPetsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(root.context)

        val samplePet = arrayOf("Waldi", "Dog", "Australian Shepherd", "Grey", "01.01.2021")
        val samplePet2 = arrayOf("Katzi", "Katze", "Mischling", "Black", "01.01.2020")
        recyclerView.adapter = MissingAdapter(arrayOf(samplePet, samplePet2))

        return root
    }
}