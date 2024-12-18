package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView

class RegisterActivity : AppCompatActivity() {
    private val credentialsManager: CredentialsManager = CredentialsManager()

    private val fullNameField: TextInputEditText
        get() = findViewById(R.id.full_name_text)
    private val fullNameLayout: TextInputLayout
        get() = findViewById(R.id.full_name_layout)
    private val emailField: TextInputEditText
        get() = findViewById(R.id.email_text)
    private val emailLayout: TextInputLayout
        get() = findViewById(R.id.email_layout)
    private val phoneField: TextInputEditText
        get() = findViewById(R.id.phone_number_text)
    private val phoneLayout: TextInputLayout
        get() = findViewById(R.id.phone_number_layout)
    private val passwordField: TextInputEditText
        get() = findViewById(R.id.password_text)
    private val passwordLayout: TextInputLayout
        get() = findViewById(R.id.password_layout)
    private val termsCheckBox: CheckBox
        get() = findViewById(R.id.terms_and_conditions_checkbox)
    private val nextButton: MaterialButton
        get() = findViewById(R.id.next)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_account)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val logInTextButton: MaterialTextView = findViewById(R.id.log_in)

        logInTextButton.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            finish()
        }

        nextButton.setOnClickListener {
            handleNextButtonClick()
        }
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
