import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cafe.adriel.voyager.navigator.Navigator
import com.samentic.bundlesignergui.screens.StartupScreen

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        Navigator(StartupScreen())
    }
}
