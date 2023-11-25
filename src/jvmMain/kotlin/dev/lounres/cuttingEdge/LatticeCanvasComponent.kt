package dev.lounres.cuttingEdge

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import dev.lounres.composeLatticeCanvas.LatticeCanvas
import dev.lounres.composeLatticeCanvas.LatticeCanvasState
import dev.lounres.kone.context.invoke
import dev.lounres.kone.misc.lattices.*
import kotlinx.coroutines.CoroutineScope


fun <C, K, V> createLatticeCanvasComponent(
    lattice: LatticeWithConnectivity<C, K, V>,
    latticeCanvas: LatticeCanvas<C, K>,
    positions: MutableMap<Position<C, K>, Unit> = mutableStateMapOf(),
    calculateCenter: context(LatticeCanvas<C, K>) Set<Position<C, K>>.() -> Offset,
) = LatticeCanvasComponent(
    lattice = lattice,
    latticeCanvas = latticeCanvas,
    positions = positions,
    latticeCanvasState = LatticeCanvasState(),
    calculateCenter = { calculateCenter(latticeCanvas, this) },
)

class LatticeCanvasComponent<C, K, V>(
    val lattice: LatticeWithConnectivity<C, K, V>,
    val latticeCanvas: LatticeCanvas<C, K>,
    val positions: MutableMap<Position<C, K>, Unit>,
    val latticeCanvasState: LatticeCanvasState,
    val calculateCenter: Set<Position<C, K>>.() -> Offset,
) {
    @Composable
    fun Content(
        modifier: Modifier = Modifier,
        tileSize: Dp = DEFAULT_TILE_SIZE,
        zoomMin: Float = ZOOM_MIN,
        zoomMax: Float = ZOOM_MAX,
    ) {
        latticeCanvas.Content(
            modifier = modifier,
            tileSize = tileSize,
            zoomMin = zoomMin,
            zoomMax = zoomMax,
            latticeCanvasState = latticeCanvasState,
            onCellClick = { position ->
                if (position in positions) positions.remove(position)
                else positions[position] = Unit
            },
            onCellDraw = { position, contour, _ ->
                if (position in positions)
                    drawPath(
                        path = contour,
                        color = Color.Gray,
                    )
            },
        )
    }

    context(CoroutineScope)
    fun MutableCollection<in PartitionPreviewComponent<*, *>>.addPartitions(numberOfParts: Int, partsAreConnected: Boolean) {
        lattice {
            val figureCenter = positions.keys.calculateCenter()
            positions
                .mapTo(HashSet()) { Cell(it.key, emptySet<Nothing>()) }
                .divideInPartsTo(
                    destination = MappingCollection(this@addPartitions) {
                        createPartitionPreviewComponent(
                            latticeCanvas = latticeCanvas,
                            parts = it,
                            figureCenter = figureCenter,
                        )
                    },
                    numberOfParts = numberOfParts,
                    takeFormIf = { !partsAreConnected || it.isConnected() },
                )
        }
    }
}


private inline fun <E, F> MappingCollection(destination: MutableCollection<F>, crossinline mapping: (E) -> F): MappingCollection<E, F> =
    object : MappingCollection<E, F>(destination) {
        override fun mapping(element: E): F = mapping(element)
    }
private abstract class MappingCollection<E, F>(
    val destination: MutableCollection<F>,
): MutableCollection<E> {
    abstract fun mapping(element: E): F

    override val size: Int get() = destination.size
    override fun clear() {
        destination.clear()
    }
    override fun addAll(elements: Collection<E>): Boolean {
        elements.mapTo(destination, ::mapping)
        return true
    }
    override fun add(element: E): Boolean = destination.add(mapping(element))
    override fun isEmpty(): Boolean = destination.isEmpty()
    override fun iterator(): MutableIterator<E> {
        error("This method should not be called")
    }
    override fun retainAll(elements: Collection<E>): Boolean {
        error("This method should not be called")
    }
    override fun removeAll(elements: Collection<E>): Boolean {
        error("This method should not be called")
    }
    override fun remove(element: E): Boolean = destination.remove(mapping(element))
    override fun containsAll(elements: Collection<E>): Boolean {
        error("This method should not be called")
    }
    override fun contains(element: E): Boolean {
        error("This method should not be called")
    }
}