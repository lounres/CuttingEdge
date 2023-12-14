package dev.lounres.cuttingEdge.components.mainWindow

import dev.lounres.cuttingEdge.allLatticeVariants
import dev.lounres.cuttingEdge.components.mainWindow.bottomCardPreviewList.BottomCardPreviewListComponent
import dev.lounres.cuttingEdge.components.mainWindow.bottomCardPreviewList.FakeBottomCardPreviewListComponent
import dev.lounres.cuttingEdge.components.mainWindow.controlPane.FakeMainPageControlPaneComponent
import dev.lounres.cuttingEdge.components.mainWindow.controlPane.MainPageControlPaneComponent
import dev.lounres.cuttingEdge.components.mainWindow.lattice.FakeMainPageLatticeComponent
import dev.lounres.cuttingEdge.components.mainWindow.lattice.MainPageLatticeComponent
import dev.lounres.cuttingEdge.uiComponents.LatticeCanvasComponent
import dev.lounres.cuttingEdge.uiComponents.PartitionPreviewComponent


class FakeMainWindowComponent(
    latticeVariants: List<LatticeCanvasComponent<*, *, *>> = allLatticeVariants,
    latticeVariantIndex: Int = 0,
    showProcessing: Boolean = false,
    partsAreConnected: Boolean = true,
    numberOfParts: Int = 1,
    partitionCardPreviewComponents: List<PartitionPreviewComponent<*, *>> = emptyList()
): MainWindowComponent {
    override val receivePartitionCardPreviews: suspend () -> Unit = {}

    override val mainPageLatticeComponent: MainPageLatticeComponent = FakeMainPageLatticeComponent(
        latticeVariants = latticeVariants,
        latticeVariantIndex = latticeVariantIndex
    )
    override val mainPageControlPaneComponent: MainPageControlPaneComponent = FakeMainPageControlPaneComponent(
        latticeVariants = latticeVariants,
        latticeVariantIndex = latticeVariantIndex,
        showProcessing = showProcessing,
        partsAreConnected = partsAreConnected,
        numberOfParts = numberOfParts,
    )
    override val bottomCardPreviewListComponent: BottomCardPreviewListComponent = FakeBottomCardPreviewListComponent(
        partitionCardPreviewComponents = partitionCardPreviewComponents,
    )

    override val onCloseRequest: () -> Unit = {}
}