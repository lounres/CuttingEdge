package dev.lounres.cuttingEdge

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.lounres.cuttingEdge.components.real.RealMainPageComponent
import dev.lounres.cuttingEdge.ui.MainPageUI
import dev.lounres.cuttingEdge.ui.components.PartitionPreviewComponent


fun main() {
//    historyFilePath.createParentDirectories()
//    if (historyFilePath.notExists()) {
//        historyFilePath.createFile()
//        historyFilePath.writer().use { it.write("[]") }
//    }

    application {
        val partitionWindowPreviewComponents: MutableList<PartitionPreviewComponent<*, *>> = remember { mutableStateListOf() }
        val mainPageComponent = remember {
            RealMainPageComponent(
                latticeVariants = allLatticeVariants,
                partitionWindowPreviewComponents = partitionWindowPreviewComponents
            )
        }

        var isOpen by remember { mutableStateOf(true) }
        if (isOpen) {

            Window(
                title = "CuttingEdge — Canvas",
                icon = windowIcon,
                onCloseRequest = { isOpen = !isOpen },
            ) {
                MainPageUI(
                    component = mainPageComponent
                )
            }
        }

        for (component in partitionWindowPreviewComponents) key(component) {
            Window(
                title = "CuttingEdge — Result",
                icon = windowIcon,
                onCloseRequest = {
                    partitionWindowPreviewComponents.remove(component)
                },
            ) {
                component.Content(Modifier.fillMaxSize())
            }
        }
    }
}