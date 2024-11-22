package com.example.myapplication

import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class CredentialsManagerTest {
    
    @Test
    fun `test empty email`() {
        val email = ""
        assertFalse(isEmailValid(email), "Empty email should be invalid")
    }

    @Test
    fun `test wrong email format`() {
        val email = "example.com"
        assertFalse(isEmailValid(email), "Email missing '@' should be invalid")
    }

    @Test
    fun `test proper email`() {
        val email = "test@example.com"
        assertTrue(isEmailValid(email), "Properly formatted email should be valid")
    }

    // Test empty password
    @Test
    fun `test empty password`() {
        val password = ""
        assertFalse(isPasswordValid(password), "Empty password should be invalid")
    }

    // Test filled password
    @Test
    fun `test filled password`() {
        val password = "securePassword123"
        assertTrue(isPasswordValid(password), "Non-empty password should be valid")
    }

    @Test
    fun givenEmptyPassword_thenReturnFalse() {
        val credentialsManager = CredentialsManager()

        val isEmailValid = credentialsManager.isEmailValid("")

        assertEquals(false, isEmailValid)
    }
}