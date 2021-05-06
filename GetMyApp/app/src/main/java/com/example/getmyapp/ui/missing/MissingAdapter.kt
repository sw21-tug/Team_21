package com.example.getmyapp.ui.missing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.getmyapp.R
import com.example.getmyapp.database.Pet

class MissingAdapter(private val dataSet: ArrayList<Pet>) :
    RecyclerView.Adapter<MissingAdapter.ViewHolder>() {

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
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
