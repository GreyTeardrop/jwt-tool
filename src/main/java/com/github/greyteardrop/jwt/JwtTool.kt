package com.github.greyteardrop.jwt

import kotlin.system.exitProcess

val commandLineParser = CommandLineParser()
val commandExecutor = CommandExecutor()

/**
 * Entry point to JWT command line tool.
 */
fun main(args: Array<String>) {
    try {
        val command = commandLineParser.parseCommandLine(args)
        if (command != null) {
            commandExecutor.execute(command)
        }
        else {
            val consoleUI = ConsoleUI(commandExecutor)
            consoleUI.run()
        }
    }
    catch (e: UserException) {
        println(e.message)
        exitProcess(e.exitStatus)
    }
}
