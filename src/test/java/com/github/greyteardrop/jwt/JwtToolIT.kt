package com.github.greyteardrop.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.auth0.jwt.interfaces.Verification
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.contrib.java.lang.system.SystemOutRule
import org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream

class JwtToolIT {

    @Rule
    @JvmField
    val systemOutRule = SystemOutRule().enableLog().muteForSuccessfulTests()

    @Rule
    @JvmField
    val systemInRule = emptyStandardInputStream()

    private val tokenVerifier = run {
        val algorithm = Algorithm.HMAC512("secret")
        JWT.require(algorithm).withIssuer("https://github.com/GreyTeardrop/jwt-tool")
    }

    @Test
    fun `JwtTool should work in interactive mode`() {
        systemInRule.provideLines("user_id", "1", "email", "invalid email", "user@home.net", "key", "value", "")

        main(emptyArray())

        assertThat(systemOutRule.log)
            .isEqualTo("""|Starting with JWT token generation. Enter blank line to finish.
                          |Enter key 1
                          |$ Enter user_id value
                          |> user_id = Enter key 2
                          |$ Enter email value
                          |> email = Invalid email entered! Enter email value
                          |> email = Enter key 3
                          |$ Enter key value
                          |> key = Enter key 4
                          |$ The JWT has been copied to your clipboard!
                          |""".trimMargin())

        val generatedToken = SystemTextClipboard().contents
        assertThat(generatedToken).isNotNull()
        verifyToken(generatedToken!!) {
            withClaim("user_id", "1")
            withClaim("email", "user@home.net")
            withClaim("key", "value")
        }
    }

    @Test
    fun `JwtTool should work in non interactive mode`() {
        main(arrayOf("create",
                     "--payload", "user_id", "100000",
                     "--payload", "email", "customer@corporation.com",
                     "--payload", "claim", "value"))

        val generatedToken = systemOutRule.log
        assertThat(generatedToken).isNotNull()
        verifyToken(generatedToken!!) {
            withClaim("user_id", "100000")
            withClaim("email", "customer@corporation.com")
            withClaim("claim", "value")
        }
    }

    /**
     * Validate [token] against generic [tokenVerifier] and custom [verification].
     */
    private inline fun verifyToken(token: String, verification: Verification.() -> Unit): DecodedJWT {
        return tokenVerifier.apply(verification).build().verify(token)
    }

}
