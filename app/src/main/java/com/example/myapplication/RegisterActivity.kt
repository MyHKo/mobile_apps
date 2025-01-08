package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : Fragment() {
    private val credentialsManager: CredentialsManager = CredentialsManager()
    private lateinit var fullNameField: TextInputEditText
    private lateinit var fullNameLayout: TextInputLayout
    private lateinit var emailField: TextInputEditText
    private lateinit var emailLayout: TextInputLayout
    private lateinit var phoneField: TextInputEditText
    private lateinit var phoneLayout: TextInputLayout
    private lateinit var passwordField: TextInputEditText
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var termsCheckBox: CheckBox
    private lateinit var nextButton: MaterialButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_create_account, container, false)
        fullNameField = view.findViewById(R.id.full_name_text)
        fullNameLayout = view.findViewById(R.id.full_name_layout)
        emailField = view.findViewById(R.id.email_text)
        emailLayout = view.findViewById(R.id.email_layout)
        phoneField = view.findViewById(R.id.phone_number_text)
        phoneLayout = view.findViewById(R.id.phone_number_layout)
        passwordField = view.findViewById(R.id.password_text)
        passwordLayout = view.findViewById(R.id.password_layout)
        termsCheckBox = view.findViewById(R.id.terms_and_conditions_checkbox)
        nextButton = view.findViewById(R.id.next)
        nextButton.setOnClickListener { handleNextButtonClick() }
        view.findViewById<TextView>(R.id.log_in).setOnClickListener {
            navigateToLogin()
        }
        return view
    }
        private fun handleNextButtonClick() {
        val fullName = fullNameField.text.toString().trim()
        val email = emailField.text.toString().trim()
        val phone = phoneField.text.toString().trim()
        val password = passwordField.text.toString().trim()
        val isTermsAccepted = termsCheckBox.isChecked

        clearError(fullNameLayout)
        clearError(emailLayout)
        clearError(phoneLayout)
        clearError(passwordLayout)

        if (fullName.isEmpty()) {
            setError(fullNameLayout, getString(R.string.name_field_empty))
            return
        }
        if (email.isEmpty() || !credentialsManager.isEmailValid(email)) {
            setError(emailLayout, getString(R.string.error_invalid_email))
            return
        }
        if (phone.isEmpty() || !credentialsManager.isValidPhone(phone)) {
            setError(phoneLayout, getString(R.string.phone_number_error))
            return
        }
        if (password.isEmpty() || !credentialsManager.isPasswordValid(password)) {
            setError(passwordLayout, getString(R.string.error_password_invalid))
            return
        }
        if (!isTermsAccepted) {
            showToast(getString(R.string.terms_and_conditions_check))
            return
        }

        proceedToNextStep(fullName, email, phone, password, isTermsAccepted)
    }

    private fun proceedToNextStep(
        fullName: String,
        email: String,
        phone: String,
        password: String,
        isTermsAccepted: Boolean
    ) {
        if (credentialsManager.validateSignUpCredentials(
                fullName,
                email,
                phone,
                password,
                isTermsAccepted
            )
        ) {
            showToast(getString(R.string.successful_log_in))
            parentFragmentManager.popBackStack()
        } else {
            showToast(getString(R.string.credentials_not_matched))
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
    private fun navigateToLogin() {
        parentFragmentManager.popBackStack()
    }
}
