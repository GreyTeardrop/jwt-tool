package com.github.greyteardrop.jwt

/**
 * Represents single command that should be executed by JWT tool.
 */
sealed class Command

/**
 * Create JWT token command.
 */
data class CreateCommand(val payload: Map<String, String>,
                         val exportToClipboard: Boolean) : Command() {
    fun withPayload(key: String, value: String): CreateCommand {
        return copy(payload = payload + (key to value))
    }
}
