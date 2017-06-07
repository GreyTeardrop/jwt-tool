package com.github.greyteardrop.jwt

/**
 * Manages text context in clipboard.
 */
interface TextClipboard {

    /**
     * Clipboard text contents. Read loads clipboard contents, write copies new value to clipboard.
     */
    var contents: String?

}
