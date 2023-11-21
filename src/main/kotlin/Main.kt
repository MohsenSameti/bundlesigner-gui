import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cafe.adriel.voyager.navigator.Navigator
import com.samentic.bundlesignergui.screens.CommandListScreen
import com.samentic.bundlesignergui.util.LocalComposeWindow

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        CompositionLocalProvider(LocalComposeWindow provides window){
            Navigator(CommandListScreen())
        }
    }
}
