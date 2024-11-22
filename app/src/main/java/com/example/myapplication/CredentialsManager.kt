package com.example.myapplication

class CredentialsManager {
    companion object {
        fun isEmailValid(email: String): Boolean {
            val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
            return email.matches(emailRegex.toRegex())
        }

        fun isPasswordValid(password: String): Boolean {
            return password.isNotEmpty()
        }
    }
}