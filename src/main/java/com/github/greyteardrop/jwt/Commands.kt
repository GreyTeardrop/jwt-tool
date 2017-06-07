package com.github.greyteardrop.jwt

/**
 * Represents single command that should be executed by JWT tool.
 */
sealed class Command

/**
 * Create JWT token command.
 */
data class CreateCommand(
    val payload: Map<String, String> = emptyMap(),
    /**
     * TODO: allow to specify from console UI/command line
     */
    val secret: String? = null,
    val exportToClipboard: Boolean = false) : Command() {

    /**
     * Returns new instance of [CreateCommand] with [key] mapped to [value] in the payload.
     */
    fun withPayload(key: String, value: String): CreateCommand = copy(payload = payload + (key to value))

}
