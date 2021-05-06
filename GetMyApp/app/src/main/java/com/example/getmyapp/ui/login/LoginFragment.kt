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
import androidx.room.Room
import com.example.getmyapp.R
import com.example.getmyapp.database.AppDatabase
import com.example.getmyapp.database.User
import org.bouncycastle.crypto.generators.SCrypt
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import java.util.*


class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

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

        val checker = Thread {
            val db = Room.databaseBuilder(
                    requireActivity().applicationContext,
                    AppDatabase::class.java, "database-name"
            ).build()

            val userDao = db.userDao()
            user = userDao.getUser(username.toString())

        }

        checker.start()
        checker.join()

        if (user == null) {
            usernameEditText.error = activity?.resources?.getString(R.string.login_error_username)
            return
        }

        val salt = user!!.salt
        val passwordHash = user!!.hash

        val passwordByteArray = charsToBytes(password)
        val hashedPassword = SCrypt.generate(passwordByteArray, android.util.Base64.decode(salt, android.util.Base64.NO_WRAP), 16384, 8, 1, 32)

        if (passwordHash != android.util.Base64.encodeToString(hashedPassword, android.util.Base64.NO_WRAP)) {
            passwordEditText.error = activity?.resources?.getString(R.string.login_error_password)
            return
        }

        passwordEditText.setText("")
        Arrays.fill(password, '\u0000')
        Arrays.fill(passwordByteArray, 0.toByte())

        findNavController().navigate(R.id.action_nav_login_to_nav_home)
    }

    fun charsToBytes(chars: CharArray): ByteArray {
        val byteBuffer: ByteBuffer = StandardCharsets.UTF_8.encode(CharBuffer.wrap(chars))
        return Arrays.copyOf(byteBuffer.array(), byteBuffer.limit())
    }

}