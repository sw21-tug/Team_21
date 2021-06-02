package com.example.getmyapp.ui.found

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.getmyapp.R
import com.example.getmyapp.database.Pet

class FoundAdapter(private val dataSet: ArrayList<Pet>) :
    RecyclerView.Adapter<FoundAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val speciesTextView: TextView = view.findViewById(R.id.speciesSampleTextView)
        val breedTextView: TextView = view.findViewById(R.id.breedSampleTextView)
        val colorTextView: TextView = view.findViewById(R.id.colorSampleTextView)
        val lastSeenTextView: TextView = view.findViewById(R.id.lastSeenSampleTextView)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.missing_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.nameTextView.text = dataSet[position].name
        viewHolder.speciesTextView.text = dataSet[position].species
        viewHolder.breedTextView.text = dataSet[position].breed
        viewHolder.colorTextView.text = dataSet[position].color
        viewHolder.lastSeenTextView.text = dataSet[position].lastSeen
        val view = viewHolder.itemView
        view.setOnClickListener{
            val bundle = bundleOf("age" to dataSet[position].age,
                "breed" to dataSet[position].breed, "chipNo" to dataSet[position].chipNo,
                "color" to dataSet[position].color, "gender" to dataSet[position].gender,
                "lastSeen" to dataSet[position].lastSeen, "name" to dataSet[position].name,
                "region" to dataSet[position].region, "species" to dataSet[position].species,
                "age" to dataSet[position].age)
            Navigation.findNavController(view)
                .navigate(R.id.action_nav_found_to_nav_extended_report, bundle)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
