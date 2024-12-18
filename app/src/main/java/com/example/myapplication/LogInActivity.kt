package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LogInActivity : AppCompatActivity() {
    private val credentialsManager = CredentialsManager()

    private val emailField: TextInputEditText
        get() = findViewById(R.id.email_text)

    private val emailLayout: TextInputLayout
        get() = findViewById(R.id.email_layout)

    private val passwordField: TextInputEditText
        get() = findViewById(R.id.password_text)

    private val passwordLayout: TextInputLayout
        get() = findViewById(R.id.password_layout)

    private val nextButton: MaterialButton
        get() = findViewById(R.id.next)

    private val rememberMeCheckbox: MaterialCheckBox
        get() = findViewById(R.id.remember_me_checkbox)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nextButton.setOnClickListener { handleNextButtonClick() }
        val loginButton = findViewById<TextView>(R.id.register)
        loginButton.setOnClickListener {
            val goToReg = Intent(this, RegisterActivity::class.java)
            startActivity(goToReg)
        }
    }

    private fun handleNextButtonClick() {
        val email = emailField.text.toString().trim()
        val password = passwordField.text.toString().trim()
        if (credentialsManager.hardcodedCredentialCheck(email, password)) {
            navigateToMainActivity()
            return
        }
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

            else -> showToast(getString(R.string.credentials_not_matched))
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

    private fun navigateToMainActivity() {
        clearError(emailLayout)
        clearError(passwordLayout)
        showToast(getString(R.string.successful_log_in))
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}
