package com.example.myapplication

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import com.example.myapplication.CredentialsManager.Companion.isEmailValid
import com.example.myapplication.CredentialsManager.Companion.isPasswordValid

class CredentialsManagerTest {

    @Test
    fun givenEmptyEmail_thenReturnFalse() {
        assertEquals(false, isEmailValid(""))
    }

    @Test
    fun givenWrongFormatEmail_thenReturnFalse() {
        assertEquals(false, isEmailValid("example.com"))
    }

    @Test
    fun givenProperEmail_thenReturnTrue() {
        assertEquals(true, isEmailValid("test@example.com"))
    }

    @Test
    fun givenFilledPassword_thenReturnTrue() {
        assertEquals(false, isPasswordValid("securePassword123"))
    }

    @Test
    fun givenEmptyPassword_thenReturnFalse() {
        assertEquals(false, isPasswordValid(""))
    }
}