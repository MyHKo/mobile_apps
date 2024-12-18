package com.example.myapplication

class CredentialsManager {

    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    private val usersList = mutableMapOf<String, String>()
    fun isEmailValid(email: String): Boolean {

        return email.matches(emailRegex.toRegex())
    }

    fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty() && password.length >= 8
    }

    fun emailExists(email: String): Boolean {
        return usersList.containsKey(email.lowercase())
    }

    fun registerUser(email: String, password: String): Boolean {
        if (emailExists(email)) {
            return false
        } else {
            usersList[email.lowercase()] = password
            return true
        }
    }

    fun validateLogin(email: String, password: String): Boolean {
        return usersList[email.lowercase()] == password
    }

    fun validateCredentials(email: String, password: String): Boolean {
        return isEmailValid(email) && isPasswordValid(password)
    }

    fun isValidFullName(fullName: String): Boolean {
        return fullName.isNotEmpty()
    }

    fun hardcodedCredentialCheck(email: String, password: String): Boolean {
        val hardcodedEmail = "test@te.st"
        val hardcodedPassword = "password12"
        return email == hardcodedEmail && password == hardcodedPassword
    }

    fun isValidPhone(phone: String): Boolean {
        val phonePattern = "^[0-9]{9,}$".toRegex()
        return phone.matches(phonePattern)
    }

    fun isTandCAccepted(checked: Boolean): Boolean {
        return checked
    }

    fun validateSignUpCredentials(
        fullName: String,
        email: String,
        phone: String,
        password: String,
        terms: Boolean
    ): Boolean {
        return isValidFullName(fullName) &&
                isEmailValid(email) &&
                isValidPhone(phone) &&
                isPasswordValid(password) &&
                isTandCAccepted(terms)
    }


}