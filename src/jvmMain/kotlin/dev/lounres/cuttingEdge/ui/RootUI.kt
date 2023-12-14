package dev.lounres.cuttingEdge.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import dev.lounres.cuttingEdge.components.RootComponent
import dev.lounres.cuttingEdge.ui.mainWindow.MainWindowUI
import dev.lounres.cuttingEdge.windowIcon


@Composable
fun RootUI(
    component: RootComponent,
) {
    val mainWindowChild = component.mainWindowSlot.subscribeAsState().value.child
    if (mainWindowChild != null) MainWindowUI(mainWindowChild.instance)

    for (preview in component.partitionWindowPreview) key(preview) {
        Window(
            title = "CuttingEdge — Result",
            icon = windowIcon,
            onCloseRequest = {
                component.partitionWindowPreview.remove(preview)
            },
        ) {
            preview.Content(Modifier.fillMaxSize())
        }
    }
}