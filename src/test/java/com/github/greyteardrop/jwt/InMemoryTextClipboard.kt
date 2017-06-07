package com.github.greyteardrop.jwt

/**
 * Fake/in-memory implementation of clipboard manager
 */
class InMemoryTextClipboard : TextClipboard {

    /**
     * Holds "clipboard value".
     */
    override var contents: String? = null

}
