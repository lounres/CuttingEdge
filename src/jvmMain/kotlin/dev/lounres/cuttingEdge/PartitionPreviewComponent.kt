package dev.lounres.cuttingEdge

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import dev.lounres.composeLatticeCanvas.LatticeCanvas
import dev.lounres.composeLatticeCanvas.LatticeCanvasState
import dev.lounres.composeLatticeCanvas.rememberLatticeCanvasState
import dev.lounres.kone.misc.lattices.Cell


fun <C, K> createPartitionPreviewComponent(
    latticeCanvas: LatticeCanvas<C, K>,
    parts: List<Set<Cell<C, K, Nothing>>>,
    figureCenter: Offset?,
) = PartitionPreviewComponent(
    latticeCanvas = latticeCanvas,
    parts = parts,
    figureCenter = figureCenter
)

class PartitionPreviewComponent<C, K>(
    val latticeCanvas: LatticeCanvas<C, K>,
    val parts: List<Set<Cell<C, K, Nothing>>>,
    val figureCenter: Offset?,
) {
    @Composable
    fun Content(
        modifier: Modifier = Modifier,
        tileSize: Dp = DEFAULT_TILE_SIZE,
        zoomMin: Float = ZOOM_MIN,
        zoomMax: Float = ZOOM_MAX,
        latticeCanvasState: LatticeCanvasState = rememberLatticeCanvasState(
            fieldOffset = latticeCanvas.latticeCoordinatesToFieldCoordinates(
                latticeOffset = figureCenter ?: Offset(0f, 0f),
                tileActualSize = LocalDensity.current.run { tileSize.toPx() }
            ),
        ),
    ) {
        latticeCanvas.Content(
            modifier = modifier,
            tileSize = tileSize,
            zoomMin = zoomMin,
            zoomMax = zoomMax,
            latticeCanvasState = latticeCanvasState,
            onCellDraw = onCellDraw@{ position, contour, _ ->
                val partsIndex = parts.indexOfFirst { Cell(position) in it }
                if (partsIndex == -1) return@onCellDraw

                drawPath(
                    path = contour,
                    color = colors[partsIndex],
                )
            },
        )
    }
}