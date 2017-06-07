package com.github.greyteardrop.jwt

import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection
import java.awt.datatransfer.Transferable

/**
 * Implementation of [TextClipboard] that interacts with real system clipboard via AWT toolkit.
 */
class SystemTextClipboard : TextClipboard {

    private val clipboard = Toolkit.getDefaultToolkit().systemClipboard

    override var contents: String?
        get() {
            val clipboardContents: Transferable = clipboard.getContents(null) ?: return null
            val bestTextFlavor: DataFlavor? = DataFlavor.selectBestTextFlavor(clipboardContents.transferDataFlavors)
            return bestTextFlavor?.getReaderForText(clipboardContents)?.use { it.readText() }
        }
        set(value) {
            val newContents = StringSelection(value)
            clipboard.setContents(newContents, newContents)
        }

}
