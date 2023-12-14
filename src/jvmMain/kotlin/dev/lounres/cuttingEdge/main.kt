package dev.lounres.cuttingEdge

import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import dev.lounres.cuttingEdge.components.RealRootComponent
import dev.lounres.cuttingEdge.ui.RootUI


fun main() {
//    historyFilePath.createParentDirectories()
//    if (historyFilePath.notExists()) {
//        historyFilePath.createFile()
//        historyFilePath.writer().use { it.write("[]") }
//    }

    val rootComponent = RealRootComponent(
        componentContext = DefaultComponentContext(
            lifecycle = LifecycleRegistry(),
        )
    )

    application {
        RootUI(rootComponent)
    }
}