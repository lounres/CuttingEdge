package dev.lounres.cuttingEdge.components

import com.arkivanov.decompose.value.Value
import dev.lounres.cuttingEdge.ui.components.LatticeCanvasComponent


interface MainPageLatticeComponent {
    val latticeVariants: List<LatticeCanvasComponent<*, *, *>>
    val latticeVariantIndex: Value<Int>
}