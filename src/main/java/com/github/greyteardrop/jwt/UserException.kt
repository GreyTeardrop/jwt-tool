package com.github.greyteardrop.jwt

/**
 * Thrown when help or error message should be displayed to user.
 */
class UserException(message: String, val exitStatus: Int = -1) : IllegalArgumentException(message)
