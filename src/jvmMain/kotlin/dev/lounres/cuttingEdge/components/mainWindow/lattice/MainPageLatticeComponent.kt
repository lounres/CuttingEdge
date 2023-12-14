package dev.lounres.cuttingEdge.components.mainWindow.lattice

import com.arkivanov.decompose.value.Value
import dev.lounres.cuttingEdge.uiComponents.LatticeCanvasComponent

interface MainPageLatticeComponent {
    val latticeVariants: List<LatticeCanvasComponent<*, *, *>>
    val latticeVariantIndex: Value<Int>
}