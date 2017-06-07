package com.github.greyteardrop.jwt

import com.github.greyteardrop.jwt.CreateCommand.PayloadKeys.emailKey
import java.io.InputStream
import java.io.PrintStream

/**
 * Manages console UI loop.
 */
class ConsoleUI(private val commandExecutor: CommandExecutor,
                stdIn: InputStream = System.`in`,
                private val stdOut: PrintStream = System.out) {

    private val reader = stdIn.bufferedReader()

    /**
     * Start console UI loop, wait for user-entered parameters
     */
    fun run() {
        var command = CreateCommand(exportToClipboard = true)
        var i = 0
        stdOut.println("Starting with JWT token generation. Enter blank line to finish.")
        stdOut.flush()
        while (true) {
            i++

            stdOut.println("Enter key $i")
            stdOut.print("$ ")
            stdOut.flush()
            val key = reader.readLine()
            if (key.isNullOrEmpty()) {
                executeCommand(command)
                // TODO: option to execute multiple commands in a loop
                return
            }

            do {
                stdOut.println("Enter $key value")
                stdOut.print("> $key = ")
                stdOut.flush()
                val value = reader.readLine()
                if (isValidPayload(key, value)) {
                    command = command.withPayload(key, value)
                    break
                }
                else {
                    stdOut.print("Invalid $key entered! ")
                }
            }
            while (true)
        }
    }

    private fun executeCommand(command: CreateCommand) {
        try {
            commandExecutor.execute(command)
        }
        catch (e: UserException) {
            // TODO: option to fix errors and continue
            throw e
        }
    }

    private fun isValidPayload(key: String, value: String?): Boolean = when (key) {
        emailKey -> value != null && EmailValidator.isValidEmail(value)
        else     -> true
    }

}
