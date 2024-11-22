// Put your package name here. Check your activity for reference.
package com.example.xyz

import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class CredentialsManagerTest {

    // Test empty email
    @Test
    fun givenEmptyPassword_thenReturnFalse() {
        val credentialsManager = CredentialsManager()

        val isEmailValid = credentialsManager.isEmailValid("")

        assertEquals(false, isEmailValid)
    }

    // Test wrong email format
    // Test proper email

    // Test empty password
    // Test filled password
}