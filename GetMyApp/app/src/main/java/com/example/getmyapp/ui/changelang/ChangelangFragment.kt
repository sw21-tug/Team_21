package com.example.getmyapp.ui.changelang

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.getmyapp.R
import java.util.*

class ChangelangFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_changelang, container, false)
        val buttonEN : Button = root.findViewById(R.id.buttonEnglish)
        buttonEN.setOnClickListener {
            setLocale("en")
        }
        val buttonRU : Button =  root.findViewById(R.id.buttonRussian)
        buttonRU.setOnClickListener {
            setLocale("ru")
        }
        return root
    }

    private fun setLocale(Lang: String) {

        val locale = Locale(Lang)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale
        activity?.baseContext?.resources?.updateConfiguration(config, activity?.baseContext?.resources?.displayMetrics)

        val editor = activity?.getSharedPreferences("Settings", Context.MODE_PRIVATE)?.edit()
        editor?.putString("My_Lang", Lang)
        editor?.apply()
        activity?.recreate()
    }
}