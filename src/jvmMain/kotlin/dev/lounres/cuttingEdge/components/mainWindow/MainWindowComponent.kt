package dev.lounres.cuttingEdge.components.mainWindow

import dev.lounres.cuttingEdge.components.mainWindow.bottomCardPreviewList.BottomCardPreviewListComponent
import dev.lounres.cuttingEdge.components.mainWindow.controlPane.MainPageControlPaneComponent
import dev.lounres.cuttingEdge.components.mainWindow.lattice.MainPageLatticeComponent


interface MainWindowComponent {
    val receivePartitionCardPreviews: suspend () -> Unit

    val mainPageLatticeComponent: MainPageLatticeComponent
    val mainPageControlPaneComponent: MainPageControlPaneComponent
    val bottomCardPreviewListComponent: BottomCardPreviewListComponent

    val onCloseRequest: () -> Unit
}