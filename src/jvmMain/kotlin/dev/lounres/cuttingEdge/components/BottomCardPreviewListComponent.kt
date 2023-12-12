package dev.lounres.cuttingEdge.components

import dev.lounres.cuttingEdge.ui.components.PartitionPreviewComponent


interface BottomCardPreviewListComponent {
    val partitionCardPreviewComponents: List<PartitionPreviewComponent<*, *>>
    fun onRemovePartitionCardPreviewComponent(index: Int)
    fun onClearPartitionCardPreviewComponents()

    fun onAddPartitionWindowPreviewComponent(previewComponent: PartitionPreviewComponent<*, *>)
}