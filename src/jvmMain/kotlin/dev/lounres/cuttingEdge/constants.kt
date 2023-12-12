package dev.lounres.cuttingEdge

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import ca.gosyer.appdirs.AppDirs
import dev.lounres.composeLatticeCanvas.QuadroSquareLatticeCanvas
import dev.lounres.composeLatticeCanvas.SquareLatticeCanvas
import dev.lounres.composeLatticeCanvas.TriangleLatticeCanvas
import dev.lounres.cuttingEdge.ui.components.LatticeCanvasComponent
import dev.lounres.cuttingEdge.ui.components.createLatticeCanvasComponent
import dev.lounres.kone.misc.lattices.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import java.nio.file.Path
import kotlin.io.path.Path


val applicationDirectories = AppDirs(
    appName = "CuttingEdge",
    appAuthor = "dev.lounres",
)

val historyFilePath: Path = Path(applicationDirectories.getUserDataDir(roaming = true)).resolve("history.json")

@OptIn(ExperimentalResourceApi::class)
val windowIcon: Painter @Composable get() = painterResource("MCCME-logo3.png")

val colors = listOf(
    Color(0xffA2F6EC),
    Color(0xff93BB63),
    Color(0xffDF697D),
    Color(0xffA9A9EF),
    Color(0xffFA7E61),
    Color(0xffFEED77),
    Color(0xffFFA6AF),
    Color(0xff83C3E5),
    Color(0xff6F90D1),
    Color(0xffFFBEEB),
)

val possibleNumbersOfParts = (1..colors.size).toList()

val DEFAULT_TILE_SIZE = 64.dp
const val ZOOM_MIN = 0.1f
const val ZOOM_MAX = 10f

val allLatticeVariants: List<LatticeCanvasComponent<*, *, *>> = listOf(
    createLatticeCanvasComponent(
        lattice = SquareLattice,
        latticeCanvas = SquareLatticeCanvas
    ) {
        if (this.isNotEmpty())
            Offset(
                (this.maxOf { it.coordinates.first + 1 } + this.minOf { it.coordinates.first }) / 2f,
                (this.maxOf { it.coordinates.second + 1 } + this.minOf { it.coordinates.second }) / 2f,
            )
        else Offset(0f, 0f)
    },
    createLatticeCanvasComponent(
        lattice = QuadroSquareLattice,
        latticeCanvas = QuadroSquareLatticeCanvas
    ) {
        if (this.isNotEmpty())
            Offset(
                (this.maxOf { it.coordinates.first + if (it.kind == QuadroSquareKind.Left) 0.5f else 1f } + this.minOf { it.coordinates.first + if (it.kind == QuadroSquareKind.Right) 0.5f else 0f }) / 2f,
                (this.maxOf { it.coordinates.second + if (it.kind == QuadroSquareKind.Down) 0.5f else 1f } + this.minOf { it.coordinates.second + if (it.kind == QuadroSquareKind.Up) 0.5f else 0f }) / 2f,
            )
        else Offset(0f, 0f)
    },
    createLatticeCanvasComponent(
        lattice = TriangleLattice,
        latticeCanvas = TriangleLatticeCanvas
    ) {
        if (this.isNotEmpty())
            Offset(
                (this.maxOf { latticeCoordinatesToFieldCoordinates(Offset(it.coordinates.first.toFloat(), it.coordinates.second.toFloat())).x + if (it.kind == TriangleKind.Up) 1f else 1.5f }
                        + this.minOf { latticeCoordinatesToFieldCoordinates(Offset(it.coordinates.first.toFloat(), it.coordinates.second.toFloat())).x + if (it.kind == TriangleKind.Up) 0f else 0.5f }) / 2f,
                (this.maxOf { latticeCoordinatesToFieldCoordinates(Offset(it.coordinates.first.toFloat(), it.coordinates.second.toFloat())).y + 0.8660254f }
                        + this.minOf { latticeCoordinatesToFieldCoordinates(Offset(it.coordinates.first.toFloat(), it.coordinates.second.toFloat())).y }) / 2f,
            )
        else Offset(0f, 0f)
    },
)