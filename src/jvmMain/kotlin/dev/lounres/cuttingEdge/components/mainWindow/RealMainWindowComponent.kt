package dev.lounres.cuttingEdge.components.mainWindow

import androidx.compose.runtime.mutableStateListOf
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import dev.lounres.cuttingEdge.components.mainWindow.bottomCardPreviewList.BottomCardPreviewListComponent
import dev.lounres.cuttingEdge.components.mainWindow.bottomCardPreviewList.RealBottomCardPreviewListComponent
import dev.lounres.cuttingEdge.components.mainWindow.controlPane.MainPageControlPaneComponent
import dev.lounres.cuttingEdge.components.mainWindow.controlPane.RealMainPageControlPaneComponent
import dev.lounres.cuttingEdge.components.mainWindow.lattice.MainPageLatticeComponent
import dev.lounres.cuttingEdge.components.mainWindow.lattice.RealMainPageLatticeComponent
import dev.lounres.cuttingEdge.uiComponents.LatticeCanvasComponent
import dev.lounres.cuttingEdge.uiComponents.PartitionPreviewComponent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch


class RealMainWindowComponent(
    val latticeVariants: List<LatticeCanvasComponent<*, *, *>>,
    partitionWindowPreviewComponents: MutableList<PartitionPreviewComponent<*, *>>,
    override val onCloseRequest: () -> Unit,
): MainWindowComponent {
    val partitionChannel: Channel<PartitionPreviewComponent<*, *>> = Channel(Channel.UNLIMITED)
    val partitionCardPreviews: MutableList<PartitionPreviewComponent<*, *>> = mutableStateListOf()

    override val receivePartitionCardPreviews: suspend () -> Unit = {
        while (true) partitionCardPreviews.add(partitionChannel.receive())
    }

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
            partitionCardPreviewComponents = partitionCardPreviews,
            partitionWindowPreviewComponents = partitionWindowPreviewComponents,
        )
}