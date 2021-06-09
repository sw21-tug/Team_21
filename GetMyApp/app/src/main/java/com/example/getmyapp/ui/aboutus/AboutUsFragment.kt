package com.example.getmyapp.ui.aboutus

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.example.getmyapp.R

class AboutUsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_aboutus, container, false)

        val web_view = root.findViewById<WebView>(R.id.webview)

        web_view.loadData(resources.getString(R.string.about_us_html), "text/html", "utf-8");

        return root
    }
}