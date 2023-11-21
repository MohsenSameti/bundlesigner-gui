package com.samentic.bundlesignergui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.samentic.bundlesignergui.util.LocalComposeWindow
import com.samentic.bundlesignergui.util.selectFileDialog
import com.samentic.bundlesignergui.util.selectSaveDirectoryDialog

class GenbinCommandScreen : Screen {

    @Composable
    override fun Content() {
        val composeWindow = LocalComposeWindow.current

        var inputBundle by remember { mutableStateOf("") }
        var inputBin by remember { mutableStateOf("") }
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
        val selectBinPathCallback = remember<() -> Unit> {
            {
                selectSaveDirectoryDialog(
                    window = composeWindow,
                    title = "Select Generated Bin Location"
                )?.let { selectedFile ->
                    inputBin = selectedFile.absolutePath
                }
            }
        }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
                .padding(12.dp)
        ) {
            // region select input bin
            Text(
                text = "Bundle File: ",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                OutlinedTextField(
                    value = inputBundle,
                    onValueChange = { inputBundle = it },
                    maxLines = 1,
                    singleLine = true,
                    placeholder = {
                        Text("Bundle File")
                    },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = selectBundleFileCallback) {
                    Text("Select")
                }
            }
            // endregion select input bin

            Spacer(modifier = Modifier.height(24.dp))

            // region select bin folder
            Text(
                text = "Bin Output path",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                OutlinedTextField(
                    value = inputBin,
                    onValueChange = { inputBin = it },
                    maxLines = 1,
                    singleLine = true,
                    placeholder = {
                        Text("Bin Directory")
                    },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = selectBinPathCallback) {
                    Text("Select")
                }
            }
            // endregion select bin folder
        }
    }
}
