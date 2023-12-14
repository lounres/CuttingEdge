package dev.lounres.cuttingEdge.components.mainWindow.controlPane

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.lounres.cuttingEdge.uiComponents.LatticeCanvasComponent
import kotlinx.coroutines.CoroutineScope


class FakeMainPageControlPaneComponent(
    override val latticeVariants: List<LatticeCanvasComponent<*, *, *>> = dev.lounres.cuttingEdge.allLatticeVariants,
    latticeVariantIndex: Int = 0,
    showProcessing: Boolean = false,
    partsAreConnected: Boolean = true,
    numberOfParts: Int = 1,
): MainPageControlPaneComponent {
    override val latticeVariantIndex: Value<Int> = MutableValue(latticeVariantIndex)
    override fun onLatticeVariantSelected(selectedLatticeVariantIndex: Int) {}

    override val showProcessing: Value<Boolean> = MutableValue(showProcessing)

    override val partsAreConnected: Value<Boolean> = MutableValue(partsAreConnected)
    override fun onPartsAreConnectedChange(newValue: Boolean) {}
    override val numberOfParts: Value<Int> = MutableValue(numberOfParts)
    override fun onNumberOfPartsChange(newNumberOfParts: Int) {}
    override fun onComputePartitions(coroutineScope: CoroutineScope) {}
}