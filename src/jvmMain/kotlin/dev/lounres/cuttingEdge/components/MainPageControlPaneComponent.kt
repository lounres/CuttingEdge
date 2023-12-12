package dev.lounres.cuttingEdge.components

import com.arkivanov.decompose.value.Value
import dev.lounres.cuttingEdge.ui.components.LatticeCanvasComponent
import kotlinx.coroutines.CoroutineScope


interface MainPageControlPaneComponent {
    val latticeVariants: List<LatticeCanvasComponent<*, *, *>>
    val latticeVariantIndex: Value<Int>
    fun onLatticeVariantSelected(selectedLatticeVariantIndex: Int)

    val showProcessing: Value<Boolean>

    val partsAreConnected: Value<Boolean>
    fun onPartsAreConnectedChange(newValue: Boolean)
    val numberOfParts: Value<Int>
    fun onNumberOfPartsChange(newNumberOfParts: Int)
    fun onComputePartitions(coroutineScope: CoroutineScope)
}