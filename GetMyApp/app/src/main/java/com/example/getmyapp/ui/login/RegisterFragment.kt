package com.example.getmyapp.ui.login

import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.getmyapp.R
import com.example.getmyapp.database.User
import org.bouncycastle.crypto.generators.SCrypt
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import java.util.*
import com.google.firebase.database.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class RegisterFragment : Fragment() {

    private lateinit var databaseUsers: DatabaseReference

    private lateinit var listOfUsers: ArrayList<User>


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_register, container, false)
        //val textView: TextView = root.findViewById(R.id.text_login)
//        loginViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        val registerButton = root.findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            getInputData()
        }

        databaseUsers = FirebaseDatabase.getInstance().getReference("Users")

        databaseUsers.addValueEventListener(userListener)

        listOfUsers = ArrayList()

        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getInputData() {
        val root = requireView()

        val usernameEditText = root.findViewById<EditText>(R.id.usernameInputEditText)
        val username = usernameEditText.text

        val firstName = root.findViewById<EditText>(R.id.firstNameEditText).text
        val lastName = root.findViewById<EditText>(R.id.lastNameEditText).text

        val mailAddressEditText = root.findViewById<EditText>(R.id.mailAddressEditText)
        val mailAddress = mailAddressEditText.text

        val phoneNumberEditText = root.findViewById<EditText>(R.id.phoneNumberEditText)
        val phoneNumber = phoneNumberEditText.text

        val passwordEditText = root.findViewById<EditText>(R.id.passwordInputEditText)
        val passwordLength = passwordEditText.length()
        val password = CharArray(passwordLength)
        passwordEditText.text.getChars(0, passwordLength, password, 0)

        val passwordConfirmationEditText = root.findViewById<EditText>(R.id.passwordInputEditText2)
        val passwordConfirmationLength = passwordConfirmationEditText.length()
        val passwordConfirmation = CharArray(passwordConfirmationLength)
        passwordConfirmationEditText.text.getChars(0, passwordConfirmationLength, passwordConfirmation, 0)

        var correctInputFlag = true



        if (username.isEmpty()) {
            usernameEditText.error = activity?.resources?.getString(R.string.error_username)
            correctInputFlag = false
        }

        if (mailAddress.isEmpty()) {
            mailAddressEditText.error = activity?.resources?.getString(R.string.register_error_mail)
            correctInputFlag = false
        }

        if (password.isEmpty()) {
            passwordEditText.error = activity?.resources?.getString(R.string.error_password)
            correctInputFlag = false
        }

        if (passwordConfirmation.isEmpty()) {
            passwordConfirmationEditText.error = activity?.resources?.getString(R.string.error_password)
            correctInputFlag = false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mailAddress).matches()) {
            mailAddressEditText.error = activity?.resources?.getString(R.string.register_error_mail_valid)
            correctInputFlag = false
        }

        if (!(password contentEquals passwordConfirmation)) {
            passwordEditText.error = activity?.resources?.getString(R.string.register_error_password_match)
            passwordConfirmationEditText.error = activity?.resources?.getString(R.string.register_error_password_match)
            correctInputFlag = false
        }

        if (phoneNumber.isNotEmpty() && !Patterns.PHONE.matcher(phoneNumber).matches()) {
            phoneNumberEditText.error = activity?.resources?.getString(R.string.register_error_phone)
            correctInputFlag = false
        }

        if (!correctInputFlag) {
            return
        }

        if (listOfUsers.find { user -> user.name.equals(username.toString()) } != null) {
            correctInputFlag = false
        }

        if (!correctInputFlag) {
            usernameEditText.error = activity?.resources?.getString(R.string.register_error_username_in_use)
            return
        }

        passwordEditText.setText("")
        passwordConfirmationEditText.setText("")

        Arrays.fill(passwordConfirmation, '\u0000')

        val passwordByteArray = charsToBytes(password)
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)

        val hashedPassword = SCrypt.generate(passwordByteArray, salt, 16384, 8, 1, 32)

        Arrays.fill(password, '\u0000')
        Arrays.fill(passwordByteArray, 0.toByte())

        val userId = databaseUsers.push().key

        val user = User(userId.toString(), username.toString(), firstName.toString(), lastName.toString(),
            mailAddress.toString(), phoneNumber.toString(),
            Base64.getEncoder().encodeToString(hashedPassword),
            Base64.getEncoder().encodeToString(salt))

        if (userId != null) {
            databaseUsers.child(userId).setValue(user)
        }

        findNavController().navigate(R.id.action_nav_register_to_nav_login)
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
                        val user = User(
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

    fun deleteTestUser(userName: String) {
        var userInDB: User? = null

        for (user in listOfUsers) {
            if (userName == user.name)
                userInDB = user
        }

        if (userInDB != null) {
            var testUser = databaseUsers.child(userInDB.userId)
            testUser.removeValue()
        }
    }

    fun getTestUser(userName: String): User? {
        var testUser: User? = null

        for (user in listOfUsers) {
            if (user.name == userName)
                testUser = user
        }

        return testUser
    }
}