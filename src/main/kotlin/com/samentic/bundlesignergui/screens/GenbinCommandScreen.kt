package com.samentic.bundlesignergui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.samentic.bundlesignergui.util.LocalComposeWindow
import com.samentic.bundlesignergui.util.selectFileDialog

class GenbinCommandScreen : Screen {

    @Composable
    override fun Content() {
        val composeWindow = LocalComposeWindow.current

        var inputBundle by remember { mutableStateOf("") }
        val selectBundleFileCallback = remember<() -> Unit> {
            {
                selectFileDialog(
                    window = composeWindow,
                    title = "Select Bundle File",
                    allowedExtensions = listOf("Bundle Files (*.aab)" to "aab"),
                    allowMultiSelection = false,
                    allowAllFileFilter = false
                ).firstOrNull()?.let { selectedFile ->
                    inputBundle = selectedFile.absolutePath
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(12.dp),
            ) {
                OutlinedTextField(
                    value = inputBundle,
                    onValueChange = { inputBundle = it },
                    maxLines = 1,
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = selectBundleFileCallback) {
                    Text("Select")
                }
            }
        }
    }
}
