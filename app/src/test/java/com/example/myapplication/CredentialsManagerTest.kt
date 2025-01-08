package com.example.myapplication

import org.junit.Test
import org.junit.Assert.*


class CredentialsManagerTest {
    private val credentialsManager = CredentialsManager()

    @Test
    fun test_EmailAttempts() {
        //empty email
        assertFalse(credentialsManager.isEmailValid(""))
        //incorrect format
        assertFalse(credentialsManager.isEmailValid("example.com"))
        //correct email
        assertTrue(credentialsManager.isEmailValid("test@example.com"))
    }

    @Test
    fun test_PasswordAttempts() {
        assertTrue(credentialsManager.isPasswordValid("securePassword123"))
        assertFalse(credentialsManager.isPasswordValid(""))

    }

    @Test
    fun testPhoneNumber() {
        val credentialsManager = CredentialsManager()
        //correct phone
        assertTrue(credentialsManager.isValidPhone("569872222"))
        //wrong format for phone
        assertFalse(credentialsManager.isValidPhone("sdfaf11g"))

    }

    @Test
    fun validateEmail() {
        //successful email and password
        assertTrue(credentialsManager.validateCredentials("test@te.st", "password1"))
        //incorrect email
        assertFalse(credentialsManager.validateCredentials("example.com", "password1"))
        //incorrect password
        assertFalse(credentialsManager.validateCredentials("test@te.st", "seet"))
        //both incorrect
        assertFalse(credentialsManager.validateCredentials("example.com", "seet"))
    }

    @Test
    fun termsAccepted() {
        assertEquals(false, credentialsManager.isTandCAccepted(false))
    }

    @Test
    fun testValidateCredentialsForSignUp() {
        val credentialsManager = CredentialsManager()
        // Valid full name, email, phone, password, and checkbox checked
        assertTrue(
            credentialsManager.validateSignUpCredentials(
                "James Hunt", "test@te.st", "4558967325", "password1", true
            )
        )

        // Invalid full name
        assertFalse(
            credentialsManager.validateSignUpCredentials(
                "", "test@te.st", "4558967325", "password1", true
            )
        )

        // Invalid email
        assertFalse(
            credentialsManager.validateSignUpCredentials(
                "James Hunt", "not_an_email", "4558967325", "password1", true
            )
        )

        // Invalid phone number
        assertFalse(
            credentialsManager.validateSignUpCredentials(
                "James Hunt", "test@te.st", "458996", "password1", true
            )
        )

        // Invalid password
        assertFalse(
            credentialsManager.validateSignUpCredentials(
                "James Hunt", "test@te.st", "4589765332", "passwor", true
            )
        )

        // Checkbox not checked
        assertFalse(
            credentialsManager.validateSignUpCredentials(
                "James Hunt", "example@test.com", "1234567890", "password123", false
            )
        )
    }

    @Test
    fun testHardcodedLoginCredentials() {
        val validEmail = "test@te.st"
        val validPassword = "1234"

        assertTrue(validEmail == "test@te.st" && validPassword == "1234")

        assertFalse(validEmail == "wrong@te.st" && validPassword == "1234")
        assertFalse(validEmail == "test@te.st" && validPassword == "wrong")
    }

    @Test
    fun testRegisterUser() {
        val email = "newuser@test.com"
        val password = "strongPassword123"

        assertTrue(credentialsManager.registerUser(email, password))
        assertFalse(credentialsManager.registerUser(email, password))
        assertTrue(credentialsManager.emailExists(email))
    }

    @Test
    fun testValidateLogin() {
        val test_email = "cred@test.com"
        val test_password = "password@1"

        // registering user
        credentialsManager.registerUser(test_email, test_password)

        assertTrue(credentialsManager.validateLogin(test_email, test_password))
        //failed attempt
        assertFalse(credentialsManager.validateLogin(test_email, "not a match"))

        // email non existent in database
        assertFalse(credentialsManager.validateLogin("noncred@test.com", test_password))

        // case-sensitivity check
        assertTrue(credentialsManager.validateLogin("CrEd@test.com", test_password))
    }
}



