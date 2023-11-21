package com.samentic.bundlesignergui.screens

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.rememberDialogState
import cafe.adriel.voyager.core.screen.Screen
import com.samentic.bundlesignergui.util.LocalComposeWindow
import com.samentic.bundlesignergui.util.selectFileDialog
import com.samentic.bundlesignergui.util.selectSaveDirectoryDialog
import java.awt.Rectangle

class GenbinCommandScreen : Screen {

    @Composable
    override fun Content() {
        val composeWindow = LocalComposeWindow.current

        var showEnvSelectDialog by remember { mutableStateOf(false) }

        var inputBundle by remember { mutableStateOf("") }
        var inputBin by remember { mutableStateOf("") }
        var inputKeyStore by remember { mutableStateOf("") }
        var inputKeyStorePassword by remember { mutableStateOf("") }
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
        val selectKeyStoreCallback = remember<() -> Unit> {
            {
                selectFileDialog(
                    window = composeWindow,
                    title = "Select KeyStore",
                    allowedExtensions = listOf("KeyStore (*.jks)" to "jks"),
                    allowMultiSelection = false,
                    allowAllFileFilter = true
                ).firstOrNull()?.let { selectedFile ->
                    inputKeyStore = selectedFile.absolutePath
                }
            }
        }
        val loadPasswordFromEnvCallback = remember<() -> Unit> {
            {
                showEnvSelectDialog = true
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

            Spacer(modifier = Modifier.height(24.dp))

            // region KeyStore
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Key Store: ",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    OutlinedTextField(
                        value = inputKeyStore,
                        onValueChange = { inputKeyStore = it },
                        maxLines = 1,
                        singleLine = true,
                        placeholder = {
                            Text("Key Store")
                        },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = selectKeyStoreCallback) {
                        Text("Select")
                    }
                }
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = "Key Store Password: ",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    OutlinedTextField(
                        value = inputKeyStorePassword,
                        onValueChange = { inputKeyStorePassword = it },
                        maxLines = 1,
                        singleLine = true,
                        placeholder = {
                            Text("Key Store Password")
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = loadPasswordFromEnvCallback) {
                        Text("From Env")
                    }
                }
            }
            // endregion KeyStore
        }

        if (showEnvSelectDialog) {
            Dialog(
                onCloseRequest = { showEnvSelectDialog = false },
                state = rememberDialogState(size = DpSize(200.dp, 400.dp)),
                title = "Select Environment Variable",
            ) {
                val environment = remember { mutableStateListOf(*System.getenv().keys.sorted().toTypedArray()) }
                val scrollState = rememberLazyListState()
                Box(modifier = Modifier.fillMaxWidth()) {
                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(environment) {
                            Text(
                                text = it,
                                modifier = Modifier.padding(4.dp)
                                    .clickable {
                                        inputKeyStorePassword = System.getenv(it).orEmpty()
                                        showEnvSelectDialog = false
                                    }
                            )
                        }
                    }
                    VerticalScrollbar(
                        modifier = Modifier.align(Alignment.CenterEnd)
                            .fillMaxHeight(),
                        adapter = rememberScrollbarAdapter(scrollState)
                    )
                }
            }
        }
    }
}
