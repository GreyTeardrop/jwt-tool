package com.github.greyteardrop.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.io.PrintStream
import java.time.Clock
import java.util.Date

/**
 * Executes single [Command]
 */
class CommandExecutor(
    private val clock: Clock = Clock.systemUTC(),
    private val stdOut: PrintStream = System.out) {

    private val userIdKey = "user_id"
    private val emailKey = "email"

    private val defaultIssuer = "https://github.com/GreyTeardrop/jwt-tool"

    /**
     * TODO: should be loaded from configuration storage.
     */
    private val defaultSecret = "secret"

    /**
     * Executes [Command]
     *
     * @throws UserException if error message has to be displayed to user due to invalid command
     */
    fun execute(command: Command): Unit = when (command) {
        is CreateCommand -> executeCreate(command)
    }

    /**
     * @throws UserException upon validation error
     */
    private fun executeCreate(command: CreateCommand) {
        command.validate()

        val secret = command.secret ?: defaultSecret
        val algorithm = Algorithm.HMAC512(secret)
        val token: String = with(JWT.create()) {
            withIssuer(defaultIssuer)
            withIssuedAt(Date(clock.millis()))
            for ((key, value) in command.payload) {
                withClaim(key, value)
            }
            sign(algorithm)
        }

        if (command.exportToClipboard) {
            copyToClipboard(token)
        }
        else {
            stdOut.println("Generated JWT token: $token")
        }
    }

    private fun copyToClipboard(token: String) {
        TODO("not implemented")
    }

    /**
     * @throws UserException upon validation error
     */
    private fun CreateCommand.validate() {
        if (!payload.containsKey(userIdKey)) {
            throw UserException("$userIdKey is required in payload")
        }

        val email = payload[emailKey] ?: throw UserException("$emailKey is required in payload")
        if (!EmailValidator.isValidEmail(email)) {
            throw UserException("Invalid email entered: $email")
        }
    }

}
