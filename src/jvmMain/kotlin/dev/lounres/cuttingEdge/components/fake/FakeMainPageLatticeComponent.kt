package dev.lounres.cuttingEdge.components.fake

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.lounres.cuttingEdge.components.MainPageLatticeComponent
import dev.lounres.cuttingEdge.allLatticeVariants
import dev.lounres.cuttingEdge.ui.components.LatticeCanvasComponent


class FakeMainPageLatticeComponent(
    override val latticeVariants: List<LatticeCanvasComponent<*, *, *>> = allLatticeVariants,
    latticeVariantIndex: Int = 0
) : MainPageLatticeComponent {
    override val latticeVariantIndex: Value<Int> = MutableValue(latticeVariantIndex)
}