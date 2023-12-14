package dev.lounres.cuttingEdge.ui.mainWindow

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import dev.lounres.cuttingEdge.components.mainWindow.MainWindowComponent
import dev.lounres.cuttingEdge.components.mainWindow.FakeMainWindowComponent
import dev.lounres.cuttingEdge.ui.mainWindow.bottomCardPreviewList.BottomCardPreviewListUI
import dev.lounres.cuttingEdge.ui.mainWindow.controlPane.MainPageControlPaneUI
import dev.lounres.cuttingEdge.ui.mainWindow.lattice.MainPageLatticeUI
import dev.lounres.cuttingEdge.uiComponents.HorizontalDivider
import dev.lounres.cuttingEdge.uiComponents.VerticalDivider
import dev.lounres.cuttingEdge.windowIcon
import kotlinx.coroutines.launch


@Preview
@Composable
fun MainWindowUIPreview() {
    MainWindowUI(component = FakeMainWindowComponent())
}

@Composable
fun MainWindowUI(
    component: MainWindowComponent,
) {
    Window(
        title = "CuttingEdge — Canvas",
        icon = windowIcon,
        onCloseRequest = component.onCloseRequest,
    ) {
        rememberCoroutineScope().launch { component.receivePartitionCardPreviews() }
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth().weight(1f)) {
                MainPageLatticeUI(
                    modifier = Modifier.weight(1f),
                    component = component.mainPageLatticeComponent
                )

                VerticalDivider()

                MainPageControlPaneUI(
                    component = component.mainPageControlPaneComponent
                )
            }

            HorizontalDivider()

            BottomCardPreviewListUI(component = component.bottomCardPreviewListComponent)
        }
    }
}