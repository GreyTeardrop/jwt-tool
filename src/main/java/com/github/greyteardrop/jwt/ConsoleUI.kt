package com.github.greyteardrop.jwt

import java.io.InputStream
import java.io.PrintStream

/**
 * Manages console UI loop.
 */
class ConsoleUI(private val commandExecutor: CommandExecutor,
                private val stdIn: InputStream = System.`in`,
                private val stdOut: PrintStream = System.out) {

    /**
     * Start console UI, listening for user para
     */
    fun run() {

    }
}
