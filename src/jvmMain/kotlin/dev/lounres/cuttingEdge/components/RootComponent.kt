package dev.lounres.cuttingEdge.components

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import dev.lounres.cuttingEdge.components.mainWindow.MainWindowComponent
import dev.lounres.cuttingEdge.uiComponents.PartitionPreviewComponent


interface RootComponent {
    val partitionWindowPreview: MutableList<PartitionPreviewComponent<*, *>>

    val mainWindowSlot: Value<ChildSlot<*, MainWindowComponent>>
}