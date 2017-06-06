package com.github.greyteardrop.jwt

import java.io.PrintStream

/**
 * Executes single [Command]
 */
class CommandExecutor(private val stdOut: PrintStream = System.out) {

    /**
     * Executes [Command]
     *
     * @throws UserException if error message has to be displayed to user due to invalid command
     */
    fun execute(command: Command): Unit = when (command) {
        is CreateCommand -> executeCreate(command)
    }

    private fun executeCreate(command: CreateCommand) {

    }
}
