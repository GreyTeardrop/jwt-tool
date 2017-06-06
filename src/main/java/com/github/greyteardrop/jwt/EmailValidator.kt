package com.github.greyteardrop.jwt

/**
 * Encapsulates email validation logic
 */
object EmailValidator {

    private val emailRegex = Regex(".+@.+")

    /**
     * Checks if user input looks like email address. Oversimplified check is done.
     */
    fun isValidEmail(email: String): Boolean = email.matches(emailRegex)

}
