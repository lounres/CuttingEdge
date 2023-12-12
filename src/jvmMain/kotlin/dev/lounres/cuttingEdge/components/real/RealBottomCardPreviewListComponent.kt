package dev.lounres.cuttingEdge.components.real

import dev.lounres.cuttingEdge.components.BottomCardPreviewListComponent
import dev.lounres.cuttingEdge.ui.components.PartitionPreviewComponent


class RealBottomCardPreviewListComponent(
    override val partitionCardPreviewComponents: MutableList<PartitionPreviewComponent<*, *>>,
    val partitionWindowPreviewComponents: MutableList<PartitionPreviewComponent<*, *>>,
): BottomCardPreviewListComponent {
    override fun onRemovePartitionCardPreviewComponent(index: Int) {
        partitionCardPreviewComponents.removeAt(index)
    }
    override fun onClearPartitionCardPreviewComponents() {
        partitionCardPreviewComponents.clear()
    }

    override fun onAddPartitionWindowPreviewComponent(previewComponent: PartitionPreviewComponent<*, *>) {
        partitionWindowPreviewComponents.add(previewComponent.copy())
    }
}