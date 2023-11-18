import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cafe.adriel.voyager.navigator.Navigator
import com.samentic.bundlesignergui.screens.StartupScreen
import ir.cafebazaar.bundlesigner.command.GenBinCommand

fun main() = application {
    GenBinCommand.Builder().build()
    Window(onCloseRequest = ::exitApplication) {
        Navigator(StartupScreen())
    }
}
