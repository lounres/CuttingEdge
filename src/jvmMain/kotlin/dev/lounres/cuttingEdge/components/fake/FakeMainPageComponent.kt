package dev.lounres.cuttingEdge.components.fake

import dev.lounres.cuttingEdge.allLatticeVariants
import dev.lounres.cuttingEdge.components.BottomCardPreviewListComponent
import dev.lounres.cuttingEdge.components.MainPageComponent
import dev.lounres.cuttingEdge.components.MainPageControlPaneComponent
import dev.lounres.cuttingEdge.components.MainPageLatticeComponent
import dev.lounres.cuttingEdge.ui.components.LatticeCanvasComponent
import dev.lounres.cuttingEdge.ui.components.PartitionPreviewComponent
import kotlinx.coroutines.CoroutineScope


class FakeMainPageComponent(
    latticeVariants: List<LatticeCanvasComponent<*, *, *>> = allLatticeVariants,
    latticeVariantIndex: Int = 0,
    showProcessing: Boolean = false,
    partsAreConnected: Boolean = true,
    numberOfParts: Int = 1,
    partitionCardPreviewComponents: List<PartitionPreviewComponent<*, *>> = emptyList()
): MainPageComponent {
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

    override fun launchPartitionsCollection(coroutineScope: CoroutineScope) {}
}