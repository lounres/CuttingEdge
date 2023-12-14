package dev.lounres.cuttingEdge.components.mainWindow.bottomCardPreviewList

import dev.lounres.cuttingEdge.uiComponents.PartitionPreviewComponent

class FakeBottomCardPreviewListComponent(
    override val partitionCardPreviewComponents: List<PartitionPreviewComponent<*, *>> = emptyList(),
): BottomCardPreviewListComponent {
    override val onRemovePartitionCardPreviewComponent: (Int) -> Unit = {}
    override val onClearPartitionCardPreviewComponents = {}
    override val onAddPartitionWindowPreviewComponent: (PartitionPreviewComponent<*, *>) -> Unit = {}
}