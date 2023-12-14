package dev.lounres.cuttingEdge.components.mainWindow.bottomCardPreviewList

import dev.lounres.cuttingEdge.uiComponents.PartitionPreviewComponent

interface BottomCardPreviewListComponent {
    val partitionCardPreviewComponents: List<PartitionPreviewComponent<*, *>>
    val onRemovePartitionCardPreviewComponent: (index: Int) -> Unit
    val onClearPartitionCardPreviewComponents: () -> Unit

    val onAddPartitionWindowPreviewComponent: (previewComponent: PartitionPreviewComponent<*, *>) -> Unit
}