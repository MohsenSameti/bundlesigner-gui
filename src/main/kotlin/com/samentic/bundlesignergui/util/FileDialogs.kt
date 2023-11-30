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
    val result = chooser.showOpenDialog(window)
    if(result != JFileChooser.APPROVE_OPTION) {
        return emptySet()
    }

    return if(allowMultiSelection) chooser.selectedFiles.toSet()
    else buildSet { chooser.selectedFile?.let { add(it) } }
}

fun selectSaveDirectoryDialog(
    window: ComposeWindow?,
    title: String?
): File? {
    val chooser = JFileChooser().apply {
        this.dialogTitle = title
        this.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        this.isMultiSelectionEnabled = false
    }
    val result = chooser.showSaveDialog(window)
    if(result != JFileChooser.APPROVE_OPTION) {
        return null
    }

    return chooser.selectedFile
}