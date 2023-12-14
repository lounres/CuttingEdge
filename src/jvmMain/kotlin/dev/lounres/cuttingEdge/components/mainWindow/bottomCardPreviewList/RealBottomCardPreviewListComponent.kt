package dev.lounres.cuttingEdge.components.mainWindow.bottomCardPreviewList

import dev.lounres.cuttingEdge.uiComponents.PartitionPreviewComponent

class RealBottomCardPreviewListComponent(
    override val partitionCardPreviewComponents: MutableList<PartitionPreviewComponent<*, *>>,
    val partitionWindowPreviewComponents: MutableList<PartitionPreviewComponent<*, *>>,
): BottomCardPreviewListComponent {
    override val onRemovePartitionCardPreviewComponent: (Int) -> Unit = {
        partitionCardPreviewComponents.removeAt(it)
    }
    override val onClearPartitionCardPreviewComponents = {
        partitionCardPreviewComponents.clear()
    }

    override val onAddPartitionWindowPreviewComponent: (PartitionPreviewComponent<*, *>) -> Unit = {
        partitionWindowPreviewComponents.add(it.copy())
    }
}