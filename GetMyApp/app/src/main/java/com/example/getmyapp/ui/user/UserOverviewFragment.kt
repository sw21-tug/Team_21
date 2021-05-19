package com.example.getmyapp.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.getmyapp.R
import com.example.getmyapp.utils.utils


class UserOverviewFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_user_overview, container, false)

        val user = utils.getLoginState(root.context)
        if (user == null) {
            findNavController().navigate(R.id.action_nav_user_overview_to_nav_home)
            return root
        }

        val username = root.findViewById<TextView>(R.id.userOverviewUsernameInfoTextView)
        username.text = user.name

        val firstName = root.findViewById<TextView>(R.id.userOverviewFirstnameInfoTextView)
        if (user.firstName == null)
            firstName.text = getString(R.string.user_overview_no_first_name)
        else
            firstName.text = user.firstName

        val lastName = root.findViewById<TextView>(R.id.userOverviewLastnameInfoTextView)
        lastName.text = user.lastName
        if (user.lastName == null)
            lastName.text = getString(R.string.user_overview_no_last_name)
        else
            lastName.text = user.lastName

        val mail = root.findViewById<TextView>(R.id.userOverviewMailInfoTextView)
            mail.text = user.mailAddress

        val phone = root.findViewById<TextView>(R.id.userOverviewPhoneInfoTextView)
        phone.text = user.phoneNumber
        if (user.phoneNumber == null)
            phone.text = getString(R.string.user_overview_no_phone)
        else
            phone.text = user.phoneNumber

        val logoutButton = root.findViewById<Button>(R.id.userOverviewLogoutButton)
        logoutButton.setOnClickListener {
            utils.removeLoginState(root.context)
            findNavController().navigate(R.id.action_nav_user_overview_to_nav_login)
        }

        return root
    }
}