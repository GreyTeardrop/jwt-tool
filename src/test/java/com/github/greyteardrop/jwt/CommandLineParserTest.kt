package com.github.greyteardrop.jwt

import com.tngtech.java.junit.dataprovider.DataProvider
import com.tngtech.java.junit.dataprovider.DataProviderRunner
import com.tngtech.java.junit.dataprovider.UseDataProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith

@RunWith(DataProviderRunner::class)
class CommandLineParserTest {

    @JvmField
    @Rule
    val thrown = ExpectedException.none()

    private val parser = CommandLineParser()

    @Test
    @UseDataProvider("successfulParseData")
    fun testSuccessfulParse(args: Array<String>, expectedCommand: Command?) {
        val command = parser.parseCommandLine(args)
        assertThat(command).isEqualTo(expectedCommand)
    }

    @Test
    @UseDataProvider("unsuccessfulParseData")
    fun testUnsuccessfulParse(args: Array<String>, expectedMessage: String) {
        thrown.expect(UserException::class.java)
        thrown.expectMessage(expectedMessage)
        parser.parseCommandLine(args)
    }

    companion object {

        @JvmStatic
        @DataProvider
        fun successfulParseData() = listOf(
            listOf(arrayOf<String>(), null),
            listOf(arrayOf("create"),
                   CreateCommand()),
            listOf(arrayOf("create", "--to-clipboard"),
                   CreateCommand(exportToClipboard = true)),
            listOf(arrayOf("create", "--payload", "email", "someone@somewhere.net"),
                   CreateCommand(mapOf("email" to "someone@somewhere.net"))))

        @JvmStatic
        @DataProvider
        fun unsuccessfulParseData() = listOf(
            listOf(arrayOf("unknown-command"), "Unknown command unknown-command"),
            listOf(arrayOf("create", "--payload"), "--payload requires 2 arguments: <key> and <value>"),
            listOf(arrayOf("create", "--payload", "key"), "--payload requires 2 arguments: <key> and <value>"),
            listOf(arrayOf("create", "--unknown-parameter"), "Unknown command --unknown-parameter"),
            listOf(arrayOf("--help"), "jwt-tool [--help] | <command> [<options>]"))

    }

}
