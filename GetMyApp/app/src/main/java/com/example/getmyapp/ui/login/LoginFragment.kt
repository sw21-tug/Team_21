package com.example.getmyapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.getmyapp.R
import com.example.getmyapp.database.User
import com.google.firebase.database.*
import org.bouncycastle.crypto.generators.SCrypt
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.StandardCharsets
import java.util.*


class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var databaseUsers: DatabaseReference

    private lateinit var listOfUsers: ArrayList<User>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel =
                ViewModelProvider(this).get(LoginViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        //val textView: TextView = root.findViewById(R.id.text_login)
//        loginViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        val register_button: Button = root.findViewById(R.id.registerButton)
        register_button.setOnClickListener {
            findNavController().navigate(R.id.action_nav_login_to_nav_register)
        }

        val loginButton = root.findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            getInputData()
        }

        databaseUsers = FirebaseDatabase.getInstance().getReference("Users")

        listOfUsers = ArrayList<User>()


        return root
    }

    fun getInputData() {
        val root = requireView()

        val usernameEditText = root.findViewById<EditText>(R.id.usernameInputEditText)
        val username = usernameEditText.text

        val passwordEditText = root.findViewById<EditText>(R.id.passwordInputEditText)
        val passwordLength = passwordEditText.length()
        val password = CharArray(passwordLength)
        passwordEditText.text.getChars(0, passwordLength, password, 0)

        var correctInputFlag = true

        if (username.isEmpty()) {
            usernameEditText.error = activity?.resources?.getString(R.string.error_username)
            correctInputFlag = false
        }


        if (password.isEmpty()) {
            passwordEditText.error = activity?.resources?.getString(R.string.error_password)
            correctInputFlag = false
        }

        if (!correctInputFlag) {
            return
        }

        var user: User? = null

        databaseUsers.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot != null) {
                    val users: HashMap<String, HashMap<String, String>> = dataSnapshot.getValue() as HashMap<String, HashMap<String, String>>
                    if (users != null) {
                        listOfUsers.clear()              // make sure list is always cleared

                        for ((key, value) in users) {
                            val name = value["name"]
                            if (name != null) {
                                val user: User = User(
                                    key, name, value["firstName"], value["lastName"], value["mailAddress"],
                                    value["phoneNumber"], value["hash"], value["salt"]
                                )
                                listOfUsers.add(user)
                            }
                        }

                        user = listOfUsers.find { user -> user.name == username.toString() }
                    }

                    if (user == null) {
                        usernameEditText.error =
                            activity?.resources?.getString(R.string.login_error_username)
                        return
                    }

                    val salt = user!!.salt
                    val passwordHash = user!!.hash

                    val passwordByteArray = charsToBytes(password)
                    val hashedPassword = SCrypt.generate(
                        passwordByteArray,
                        android.util.Base64.decode(salt, android.util.Base64.NO_WRAP),
                        16384,
                        8,
                        1,
                        32
                    )

                    if (passwordHash != android.util.Base64.encodeToString(
                            hashedPassword,
                            android.util.Base64.NO_WRAP
                        )
                    ) {
                        passwordEditText.error =
                            activity?.resources?.getString(R.string.login_error_password)
                        return
                    }

                    passwordEditText.setText("")
                    Arrays.fill(password, '\u0000')
                    Arrays.fill(passwordByteArray, 0.toByte())

                    findNavController().navigate(R.id.action_nav_login_to_nav_home)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }

    fun charsToBytes(chars: CharArray): ByteArray {
        val byteBuffer: ByteBuffer = StandardCharsets.UTF_8.encode(CharBuffer.wrap(chars))
        return Arrays.copyOf(byteBuffer.array(), byteBuffer.limit())
    }

    val userListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val users: HashMap<String, HashMap<String, String>>? = dataSnapshot.getValue() as HashMap<String, HashMap<String, String>>?

            if (users != null) {
                listOfUsers.clear()              // make sure list is always cleared

                for ((key, value) in users) {
                    val name = value["name"]
                    if (name != null) {
                        val user: User = User(
                            key, name, value["firstName"], value["lastName"], value["mailAddress"],
                            value["phoneNumber"], value["hash"], value["salt"]
                        )
                        listOfUsers.add(user)
                    }
                }
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed
        }
    }
}