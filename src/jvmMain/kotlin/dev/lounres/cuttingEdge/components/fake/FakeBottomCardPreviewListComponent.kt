package dev.lounres.cuttingEdge.components.fake

import dev.lounres.cuttingEdge.components.BottomCardPreviewListComponent
import dev.lounres.cuttingEdge.ui.components.PartitionPreviewComponent


class FakeBottomCardPreviewListComponent(
    override val partitionCardPreviewComponents: List<PartitionPreviewComponent<*, *>> = emptyList(),
): BottomCardPreviewListComponent {
    override fun onRemovePartitionCardPreviewComponent(index: Int) {}
    override fun onClearPartitionCardPreviewComponents() {}
    override fun onAddPartitionWindowPreviewComponent(previewComponent: PartitionPreviewComponent<*, *>) {}
}