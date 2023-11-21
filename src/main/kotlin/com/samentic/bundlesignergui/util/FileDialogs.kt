package com.samentic.bundlesignergui.util

import androidx.compose.ui.awt.ComposeWindow
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

fun selectFileDialog(
    window: ComposeWindow?,
    title: String?,
    allowedExtensions: List<Pair<String?, String>>,
    allowMultiSelection: Boolean = true,
    allowAllFileFilter: Boolean = false
): Set<File> {
    val chooser = JFileChooser().apply {
        this.dialogTitle = title
        this.fileSelectionMode = JFileChooser.FILES_ONLY
        allowedExtensions.forEach { (description, extension) ->
            addChoosableFileFilter(FileNameExtensionFilter(description, extension))
        }
        this.isMultiSelectionEnabled = allowMultiSelection
        this.isAcceptAllFileFilterUsed = allowAllFileFilter || allowedExtensions.isEmpty()
    }
    chooser.showOpenDialog(window)

    return if(allowMultiSelection) chooser.selectedFiles.toSet()
    else buildSet { chooser.selectedFile?.let { add(it) } }
}