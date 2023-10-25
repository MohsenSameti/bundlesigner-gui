package com.samentic.bundlesignergui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

class StartupScreen : Screen {

    @Composable
    override fun Content() {

        LaunchedEffect(Unit) {
            println("getting java version...")
            runCatching {
                val process = ProcessBuilder()
                    .command("java", "-version")
                    .redirectErrorStream(true)
                    .start()
                process.inputStream.bufferedReader().readText()
            }.onFailure {
                it.printStackTrace()
            }.onSuccess {
                println(it)
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("Checking Java Version ...")
        }
    }
}