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
import kotlinx.coroutines.channels.SendChannel


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
    suspend fun SendChannel<PartitionPreviewComponent<*, *>>.addPartitions(numberOfParts: Int, partsAreConnected: Boolean) {
        lattice {
            val figureCenter = positions.keys.calculateCenter()
            val partitions = positions
                .mapTo(HashSet()) { Cell(it.key, emptySet<Nothing>()) }
                .divideInParts(
                    numberOfParts = numberOfParts,
                    takeFormIf = { !partsAreConnected || it.isConnected() },
                )
            for (partition in partitions) this@addPartitions.send(
                PartitionPreviewComponent(
                    latticeCanvas = latticeCanvas,
                    parts = partition,
                    figureCenter = figureCenter,
                )
            )
        }
    }
}