package com.example.myapplication


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import android.widget.Toast

import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class LogInActivity : Fragment() {
    private val credentialsManager = CredentialsManager()

    private lateinit var emailField: TextInputEditText
    private lateinit var emailLayout: TextInputLayout
    private lateinit var passwordField: TextInputEditText
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var nextButton: MaterialButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_login, container, false)
        emailField = view.findViewById(R.id.email_text)
        emailLayout = view.findViewById(R.id.email_layout)
        passwordField = view.findViewById(R.id.password_text)
        passwordLayout = view.findViewById(R.id.password_layout)
        nextButton = view.findViewById(R.id.next)
        nextButton.setOnClickListener { handleNextButtonClick() }
        view.findViewById<TextView>(R.id.register).setOnClickListener {
            navigateToRegister()
        }
        return view
    }

    private fun handleNextButtonClick() {
        val email = emailField.text.toString().trim()
        val password = passwordField.text.toString().trim()

        when {
            email.isEmpty() -> setError(emailLayout, getString(R.string.error_empty_email))
            !credentialsManager.isEmailValid(email) -> setError(
                emailLayout,
                getString(R.string.error_invalid_email)
            )

            password.isEmpty() -> setError(
                passwordLayout,
                getString(R.string.error_password_empty)
            )

            !credentialsManager.isPasswordValid(password) -> setError(
                passwordLayout,
                getString(R.string.error_password_invalid)
            )

            else ->  {
            if (credentialsManager.hardcodedCredentialCheck(email, password) ||
                credentialsManager.validateCredentials(email, password)
            ) {
                navigateToMain()
            } else {
                showToast("Credentials do not exist!")
            }
        }
        }
    }

    private fun setError(layout: TextInputLayout, message: String) {
        layout.error = message
    }

    private fun clearError(layout: TextInputLayout) {
        layout.error = null
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    private fun navigateToMain() {
        showToast("successfully signed in")
        parentFragmentManager.beginTransaction()
            .replace(R.id.mainFragmentView,MainFragment())
            .commit()
    }
        private fun navigateToRegister() {

        parentFragmentManager.beginTransaction()
            .replace(R.id.mainFragmentView, RegisterActivity())
            .addToBackStack(null)
            .commit()
    }
}
