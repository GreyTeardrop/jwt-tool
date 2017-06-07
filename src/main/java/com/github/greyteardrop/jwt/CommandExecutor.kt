package com.github.greyteardrop.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.github.greyteardrop.jwt.CreateCommand.PayloadKeys.emailKey
import com.github.greyteardrop.jwt.CreateCommand.PayloadKeys.userIdKey
import java.time.Clock
import java.util.Date

/**
 * Executes single [Command]
 */
class CommandExecutor(
    private val clock: Clock = Clock.systemUTC()) {

    private val defaultIssuer = "https://github.com/GreyTeardrop/jwt-tool"

    /**
     * TODO: should be loaded from configuration storage.
     */
    private val defaultSecret = "secret"

    /**
     * Executes [Command], returns results that should be displayed to user.
     *
     * @throws UserException if error message has to be displayed to user due to invalid command
     */
    fun execute(command: Command): Any? = when (command) {
        is CreateCommand -> executeCreate(command)
    }

    /**
     * @throws UserException upon validation error
     */
    private fun executeCreate(command: CreateCommand): Any? {
        // TODO: this method might delegate to another class when there's more than one command
        command.validate()
        val token = createToken(command)

        if (command.exportToClipboard) {
            exportToClipboard(token)
            return null
        }
        else {
            return token
        }
    }

    private fun createToken(command: CreateCommand): String {
        val tokenSecret = command.secret ?: defaultSecret
        val signAlgorithm = Algorithm.HMAC512(tokenSecret)
        return with(JWT.create()) {
            withIssuer(defaultIssuer)
            withIssuedAt(Date(clock.millis()))
            for ((key, value) in command.payload) {
                withClaim(key, value)
            }
            sign(signAlgorithm)
        }
    }

    private fun exportToClipboard(token: String) {
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
