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

    private lateinit var missingViewModel: MissingViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        missingViewModel =
                ViewModelProvider(this).get(MissingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_missing, container, false)
        val textView: TextView = root.findViewById(R.id.title_missing)

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

        val recyclerView = root.findViewById<RecyclerView>(R.id.missingPetsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(root.context)

        val samplePet = arrayOf("Waldi", "Dog", "Australian Shepherd", "Grey", "01.01.2021")
        val samplePet2 = arrayOf("Katzi", "Katze", "Mischling", "Black", "01.01.2020")
        recyclerView.adapter = MissingAdapter(arrayOf(samplePet, samplePet2, samplePet, samplePet2))

        missingViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}