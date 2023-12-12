package dev.lounres.cuttingEdge.components.real

import com.arkivanov.decompose.value.Value
import dev.lounres.cuttingEdge.components.MainPageLatticeComponent
import dev.lounres.cuttingEdge.ui.components.LatticeCanvasComponent


class RealMainPageLatticeComponent(
    override val latticeVariants: List<LatticeCanvasComponent<*, *, *>>,
    override val latticeVariantIndex: Value<Int>
) : MainPageLatticeComponent