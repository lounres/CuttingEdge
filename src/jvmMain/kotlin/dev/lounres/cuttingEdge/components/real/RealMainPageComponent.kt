package dev.lounres.cuttingEdge.components.real

import androidx.compose.runtime.mutableStateListOf
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import dev.lounres.cuttingEdge.components.BottomCardPreviewListComponent
import dev.lounres.cuttingEdge.components.MainPageComponent
import dev.lounres.cuttingEdge.components.MainPageControlPaneComponent
import dev.lounres.cuttingEdge.components.MainPageLatticeComponent
import dev.lounres.cuttingEdge.ui.components.LatticeCanvasComponent
import dev.lounres.cuttingEdge.ui.components.PartitionPreviewComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch


class RealMainPageComponent(
    val latticeVariants: List<LatticeCanvasComponent<*, *, *>>,
    partitionWindowPreviewComponents: MutableList<PartitionPreviewComponent<*, *>>
): MainPageComponent {
    val partitionChannel: Channel<PartitionPreviewComponent<*, *>> = Channel(Channel.UNLIMITED)
    val partitionCardPreviewComponents: MutableList<PartitionPreviewComponent<*, *>> = mutableStateListOf()

    val latticeVariantIndex: MutableValue<Int> = MutableValue(0)
    val showProcessing: MutableValue<Boolean> = MutableValue(false)
    val partsAreConnected: MutableValue<Boolean> = MutableValue(true)
    val numberOfParts: MutableValue<Int> = MutableValue(1)

    override val mainPageLatticeComponent: MainPageLatticeComponent = RealMainPageLatticeComponent(
        latticeVariants = latticeVariants,
        latticeVariantIndex = latticeVariantIndex,
    )

    override val mainPageControlPaneComponent: MainPageControlPaneComponent = RealMainPageControlPaneComponent(
        latticeVariants = latticeVariants,
        latticeVariantIndex = latticeVariantIndex,
        onLatticeVariantSelected = { selectedLatticeVariantIndex -> latticeVariantIndex.update { selectedLatticeVariantIndex } },
        showProcessing = showProcessing,
        partsAreConnected = partsAreConnected,
        onPartsAreConnectedChange = { newValue -> partsAreConnected.update { newValue } },
        numberOfParts = numberOfParts,
        onNumberOfPartsChange = { newNumberOfParts -> numberOfParts.update { newNumberOfParts } },
        onComputePartitions = {
            showProcessing.update { true }
            launch {
                with(latticeVariants[latticeVariantIndex.value]) {
                    partitionChannel.addPartitions(
                        numberOfParts = numberOfParts.value,
                        partsAreConnected = partsAreConnected.value
                    )
                }
                showProcessing.update { false }
            }
        },
    )

    override val bottomCardPreviewListComponent: BottomCardPreviewListComponent =
        RealBottomCardPreviewListComponent(
            partitionCardPreviewComponents = partitionCardPreviewComponents,
            partitionWindowPreviewComponents = partitionWindowPreviewComponents,
        )

    override fun launchPartitionsCollection(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            while (true) partitionCardPreviewComponents.add(partitionChannel.receive())
        }
    }
}