package com.example.getmyapp.ui.iap

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.getmyapp.R
import java.util.*

class InAppPurchaseFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_iap, container, false)
        val buttonDonate: Button = root.findViewById(R.id.buttonDonate)
        buttonDonate.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://paypal.com/")
            startActivity(openURL)
        }
        return root
    }
}