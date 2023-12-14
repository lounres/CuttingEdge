package dev.lounres.cuttingEdge.components.mainWindow.lattice

import com.arkivanov.decompose.value.Value
import dev.lounres.cuttingEdge.uiComponents.LatticeCanvasComponent

class RealMainPageLatticeComponent(
    override val latticeVariants: List<LatticeCanvasComponent<*, *, *>>,
    override val latticeVariantIndex: Value<Int>
) : MainPageLatticeComponent