package com.samentic.bundlesignergui.util

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.awt.ComposeWindow

val LocalComposeWindow = staticCompositionLocalOf<ComposeWindow> {
    error("no compose window provided")
}