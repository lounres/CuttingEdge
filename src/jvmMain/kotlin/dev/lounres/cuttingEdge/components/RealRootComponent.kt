package dev.lounres.cuttingEdge.components

import androidx.compose.runtime.mutableStateListOf
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import dev.lounres.cuttingEdge.allLatticeVariants
import dev.lounres.cuttingEdge.components.mainWindow.RealMainWindowComponent
import dev.lounres.cuttingEdge.uiComponents.PartitionPreviewComponent
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer


class RealRootComponent(
    val componentContext: ComponentContext,
): RootComponent {
    override val partitionWindowPreview: MutableList<PartitionPreviewComponent<*, *>> = mutableStateListOf()

    val mainWindowSlotNavigation = SlotNavigation<MainWindowConfiguration>()

    override val mainWindowSlot = componentContext.childSlot(
        source = mainWindowSlotNavigation,
        serializer = serializer(),
        initialConfiguration = { MainWindowConfiguration },
        key = "",
    ) { _, componentContext ->
        RealMainWindowComponent(
            latticeVariants = allLatticeVariants,
            partitionWindowPreviewComponents = partitionWindowPreview,
            onCloseRequest = { mainWindowSlotNavigation.dismiss() }
        )
    }

    @Serializable
    data object MainWindowConfiguration
}