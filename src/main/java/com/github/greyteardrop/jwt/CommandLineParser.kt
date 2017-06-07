package com.github.greyteardrop.jwt

/**
 * Parses command line parameters and creates [Command] that should be executed.
 */
class CommandLineParser {

    private val supportedCommands = setOf("create")

    /**
     * Parses [args] and creates [Command] that should be executed, if any.
     *
     * Returns successfully parsed [Command] or `null` if no command was provided.
     * @param args command line parameters
     * @throws UserException if error/help message has to be displayed to user
     */
    fun parseCommandLine(args: Array<String>): Command? {
        if (args.isEmpty()) {
            return null
        }

        // TODO: as there's only one command now, we create it from the start
        // TODO: to support multiple commands, command has to be instantiated when command line argument is parsed
        var command = CreateCommand()
        var i = 0
        while (i < args.size) {
            when (args[i]) {
                "--to-clipboard" -> command = command.copy(exportToClipboard = true)
                "--payload"      -> {
                    if (i + 2 >= args.size) {
                        throw UserException(
                            "--payload requires 2 arguments: <key> and <value>")
                    }
                    val key = args[i + 1]
                    val value = args[i + 2]
                    command = command.withPayload(key, value)
                    i += 2
                }
                "--help"         -> displayHelp()
                else             -> {
                    val commandName = args[i]
                    if (!supportedCommands.contains(commandName)) {
                        throw UserException(
                            "Unknown command $commandName, supportedCommands: $supportedCommands")
                    }
                }
            }
            i++
        }

        return command
    }

    private fun displayHelp(): Nothing {
        // TODO: Help text might be built from list of available options, might be loaded from resource
        throw UserException("""|jwt-tool [--help] | <command> [<options>]
                               |  Commands:
                               |    create [--to-clipboard|--payload <key> <value>]""".trimMargin(), 0)
    }

}
