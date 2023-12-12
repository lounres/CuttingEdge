package dev.lounres.cuttingEdge.components.real

import com.arkivanov.decompose.value.Value
import dev.lounres.cuttingEdge.components.MainPageControlPaneComponent
import dev.lounres.cuttingEdge.ui.components.LatticeCanvasComponent
import kotlinx.coroutines.CoroutineScope


inline fun RealMainPageControlPaneComponent(
    latticeVariants: List<LatticeCanvasComponent<*, *, *>>,
    latticeVariantIndex: Value<Int>,
    crossinline onLatticeVariantSelected: (selectedLatticeVariantIndex: Int) -> Unit,
    showProcessing: Value<Boolean>,
    partsAreConnected: Value<Boolean>,
    crossinline onPartsAreConnectedChange: (newValue: Boolean) -> Unit,
    numberOfParts: Value<Int>,
    crossinline onNumberOfPartsChange: (newNumberOfParts: Int) -> Unit,
    crossinline onComputePartitions: CoroutineScope.() -> Unit,
): RealMainPageControlPaneComponent =
    object : RealMainPageControlPaneComponent(
        latticeVariants = latticeVariants,
        latticeVariantIndex = latticeVariantIndex,
        showProcessing = showProcessing,
        partsAreConnected = partsAreConnected,
        numberOfParts = numberOfParts
    ) {
        override fun onLatticeVariantSelected(selectedLatticeVariantIndex: Int) =
            onLatticeVariantSelected(selectedLatticeVariantIndex)
        override fun onPartsAreConnectedChange(newValue: Boolean) =
            onPartsAreConnectedChange(newValue)
        override fun onNumberOfPartsChange(newNumberOfParts: Int) =
            onNumberOfPartsChange(newNumberOfParts)
        override fun onComputePartitions(coroutineScope: CoroutineScope) =
            onComputePartitions(coroutineScope)

    }

abstract class RealMainPageControlPaneComponent(
    override val latticeVariants: List<LatticeCanvasComponent<*, *, *>>,
    override val latticeVariantIndex: Value<Int>,
    override val showProcessing: Value<Boolean>,
    override val partsAreConnected: Value<Boolean>,
    override val numberOfParts: Value<Int>,
): MainPageControlPaneComponent