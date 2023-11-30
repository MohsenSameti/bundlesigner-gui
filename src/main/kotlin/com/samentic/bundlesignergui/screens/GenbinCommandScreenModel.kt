package com.samentic.bundlesignergui.screens

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File

class GenbinCommandScreenModel : ScreenModel {

    private val _state = MutableStateFlow<State>(State.Idle)
    val state = _state.asStateFlow()

    val inputBundle = mutableStateOf("")
    val inputBin = mutableStateOf("")
    val inputKeyStore = mutableStateOf("")
    val inputKeyStorePassword = mutableStateOf("")
    val isV2SingingEnabled = mutableStateOf(true)
    val isV3SingingEnabled = mutableStateOf(false)

    fun actionGenerate() {
        val map = mutableMapOf<String, String>()

        if (inputBundle.value.isBlank()) {
            map[::inputBundle.name] = "This cannot be empty"
        } else {
            if (!File(inputBundle.value).exists()) {
                map[::inputBundle.name] = "Bundle file does not exists!"
            }
        }

        if (inputBin.value.isNotBlank()) {
            val file = File(inputBin.value)
            if (file.exists()) {
                map[::inputBin.name] = "Bin directory does not exists!"
            } else if (!file.isDirectory) {
                map[::inputBin.name] = "Not a Valid Directory"
            }
        }

        if (inputKeyStore.value.isBlank()) {
            map[::inputKeyStore.name] = "This cannot be empty"
        } else {
            if (!File(inputKeyStore.value).exists()) {
                map[::inputKeyStore.name] = "Bundle file does not exists!"
            }
        }

        if (inputKeyStorePassword.value.isEmpty()) {
            map[::inputKeyStorePassword.name] = "This cannot be empty"
        }

        if (map.isNotEmpty()) {
            _state.value = State.Error(
                bundleError = map[::inputBundle.name],
                binPathError = map[::inputBin.name],
                keyStorePathError = map[::inputKeyStore.name],
                keyStorePasswordError = map[::inputKeyStorePassword.name]
            )
            return
        }
        _state.value = State.Success(
            bundlePath = inputBundle.value,
            binPath = inputBin.value.ifBlank { "." },
            keyStorePath = inputKeyStore.value,
            keyStorePassword = inputBundle.value,
            isV2SingingEnabled = isV2SingingEnabled.value,
            isV3SingingEnabled = isV3SingingEnabled.value
        )
    }


    sealed interface State {
        object Idle : State

        data class Success(
            val bundlePath: String,
            val binPath: String,
            val keyStorePath: String,
            val keyStorePassword: String,
            val isV2SingingEnabled: Boolean,
            val isV3SingingEnabled: Boolean
        ) : State

        data class Error(
            val bundleError: String? = null,
            val binPathError: String? = null,
            val keyStorePathError: String? = null,
            val keyStorePasswordError: String? = null,
            // FIXME: this is not a good fix (this is added so each time error is set recompose happens
            private val time: Long = System.currentTimeMillis()
        ) : State
    }
}