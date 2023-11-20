package dev.lounres.cuttingEdge

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import dev.lounres.composeLatticeCanvas.QuadroSquareLatticeCanvas
import dev.lounres.composeLatticeCanvas.SquareLatticeCanvas
import dev.lounres.composeLatticeCanvas.TriangleLatticeCanvas
import dev.lounres.kone.context.invoke
import dev.lounres.kone.misc.lattices.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


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

enum class LatticeVariant {
    Square,
    QuadroSquare,
    Triangular,
}

fun main() {
    application {
        val resultWindows: MutableList<ResultWindow> = remember { mutableStateListOf() }

        var isOpen by remember { mutableStateOf(true) }
        if (isOpen) Window(
            title = "CuttingEdge",
            icon = windowIcon,
            onCloseRequest = { isOpen = !isOpen },
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                var latticeVariant by remember { mutableStateOf(LatticeVariant.Square) }
                var partsAreConnected by remember { mutableStateOf(true) }
                val squarePositions: MutableMap<Position<Pair<Int, Int>, SquareKind>, Unit> = remember { mutableStateMapOf() }
                val quadroSquarePositions: MutableMap<Position<Pair<Int, Int>, QuadroSquareKind>, Unit> = remember { mutableStateMapOf() }
                val triangularPositions: MutableMap<Position<Pair<Int, Int>, TriangleKind>, Unit> = remember { mutableStateMapOf() }

                when (latticeVariant) {
                    LatticeVariant.Square ->
                        SquareLatticeCanvas.Content(
                            modifier = Modifier.fillMaxSize(),
                            onCellClick = { position ->
                                if (position in squarePositions) squarePositions.remove(position)
                                else squarePositions[position] = Unit
                            },
                            onCellDraw = { position, tileSize ->
                                if (position in squarePositions)
                                    drawCircle(
                                        color = Color.Gray,
                                        center = Offset(0f, 0f),
                                        radius = tileSize * 2,
                                    )
                            }
                        )
                    LatticeVariant.QuadroSquare ->
                        QuadroSquareLatticeCanvas.Content(
                            modifier = Modifier.fillMaxSize(),
                            onCellClick = { position ->
                                if (position in quadroSquarePositions) quadroSquarePositions.remove(position)
                                else quadroSquarePositions[position] = Unit
                            },
                            onCellDraw = { position, tileSize ->
                                if (position in quadroSquarePositions)
                                    drawCircle(
                                        color = Color.Gray,
                                        center = Offset(0f, 0f),
                                        radius = tileSize * 2,
                                    )
                            }
                        )
                    LatticeVariant.Triangular ->
                        TriangleLatticeCanvas.Content(
                            modifier = Modifier.fillMaxSize(),
                            onCellClick = { position ->
                                if (position in triangularPositions) triangularPositions.remove(position)
                                else triangularPositions[position] = Unit
                            },
                            onCellDraw = { position, tileSize ->
                                if (position in triangularPositions)
                                    drawCircle(
                                        color = Color.Gray,
                                        center = Offset(0f, 0f),
                                        radius = tileSize * 2,
                                    )
                            }
                        )
                }
                Surface(
                    modifier = Modifier.align(Alignment.TopEnd).fillMaxHeight().width(500.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        var showProcessing by remember { mutableStateOf(false) }
                        if (showProcessing) LinearProgressIndicator(
                            modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter)
                        )
                        Column(
                            modifier = Modifier.fillMaxSize().padding(10.dp),
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                val cornerRadius = 16.dp

                                OutlinedButton(
                                    modifier = Modifier.offset(2.dp, 0.dp),
                                    enabled = latticeVariant != LatticeVariant.Square,
                                    shape = RoundedCornerShape(
                                        topStart = cornerRadius,
                                        topEnd = 0.dp,
                                        bottomStart = cornerRadius,
                                        bottomEnd = 0.dp
                                    ),
                                    onClick = { latticeVariant = LatticeVariant.Square },
                                ) {
                                    Text("Квадратная")
                                }

                                OutlinedButton(
                                    enabled = latticeVariant != LatticeVariant.QuadroSquare,
                                    shape = RoundedCornerShape(
                                        topStart = 0.dp,
                                        topEnd = 0.dp,
                                        bottomStart = 0.dp,
                                        bottomEnd = 0.dp
                                    ),
                                    onClick = { latticeVariant = LatticeVariant.QuadroSquare },
                                ) {
                                    Text("Четверть-квадратная")
                                }

                                OutlinedButton(
                                    modifier = Modifier.offset((-2).dp, 0.dp),
                                    enabled = latticeVariant != LatticeVariant.Triangular,
                                    shape = RoundedCornerShape(
                                        topStart = 0.dp,
                                        topEnd = cornerRadius,
                                        bottomStart = 0.dp,
                                        bottomEnd = cornerRadius
                                    ),
                                    onClick = { latticeVariant = LatticeVariant.Triangular },
                                ) {
                                    Text("Треугольная")
                                }
                            }

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    text = "Связные части: ",
                                )
                                Checkbox(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    checked = partsAreConnected,
                                    onCheckedChange = { partsAreConnected = it },
                                )
                            }

                            var numberOfParts by remember { mutableIntStateOf(1) }
                            Row(
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    text = "Количество частей: ",
                                )
                                var showPossibleNumbersOfParts by remember { mutableStateOf(false) }
                                Box(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                ) {
                                    OutlinedButton(
                                        onClick = { showPossibleNumbersOfParts = true }
                                    ) {
                                        Text(
                                            text = numberOfParts.toString(),
                                        )
                                    }
                                    DropdownMenu(
                                        expanded = showPossibleNumbersOfParts,
                                        onDismissRequest = { showPossibleNumbersOfParts = false }
                                    ) {
                                        possibleNumbersOfParts.forEach { number ->
                                            DropdownMenuItem(
                                                onClick = {
                                                    numberOfParts = number
                                                    showPossibleNumbersOfParts = false
                                                }
                                            ) {
                                                Text(number.toString())
                                            }
                                        }
                                    }
                                }
                            }

                            val coroutineScope = rememberCoroutineScope { Dispatchers.Default }
                            Button(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                onClick = {
                                    showProcessing = true
                                    coroutineScope.launch {
                                        when(latticeVariant) {
                                            LatticeVariant.Square -> SquareLattice {
                                                val divisions = squarePositions
                                                    .map { Cell(it.key, emptySet<Nothing>()) }
                                                    .toSet()
                                                    .divideInParts(
                                                        numberOfParts = numberOfParts,
                                                        takeFormIf = {
                                                             !partsAreConnected || run {
                                                                 val startPosition = it.first()
                                                                 val positionsToTest = ArrayDeque<Position<Pair<Int, Int>, SquareKind>>()
                                                                 positionsToTest.add(startPosition)
                                                                 val testedPositions = mutableSetOf<Position<Pair<Int, Int>, SquareKind>>()
                                                                 while (positionsToTest.isNotEmpty()) {
                                                                     val nextPosition = positionsToTest.removeFirst()
                                                                     testedPositions.add(nextPosition)
                                                                     val adjacentPositions = listOf(
                                                                         Position(Pair(nextPosition.coordinates.first+1, nextPosition.coordinates.second), nextPosition.kind),
                                                                         Position(Pair(nextPosition.coordinates.first-1, nextPosition.coordinates.second), nextPosition.kind),
                                                                         Position(Pair(nextPosition.coordinates.first, nextPosition.coordinates.second+1), nextPosition.kind),
                                                                         Position(Pair(nextPosition.coordinates.first, nextPosition.coordinates.second-1), nextPosition.kind),
                                                                     )
                                                                     for (position in adjacentPositions) if (position !in testedPositions && position in it) positionsToTest.add(position)
                                                                 }
                                                                 testedPositions.size == it.size
                                                             }
                                                        },
                                                    )
                                                resultWindows.addAll(divisions.map { ResultWindow(ResultWindow.Data.Square(it)) })
                                            }
                                            LatticeVariant.QuadroSquare -> QuadroSquareLattice {
                                                val divisions = quadroSquarePositions
                                                    .map { Cell(it.key, emptySet<Nothing>()) }
                                                    .toSet()
                                                    .divideInParts(
                                                        numberOfParts = numberOfParts,
                                                        takeFormIf = {
                                                            !partsAreConnected || run {
                                                                val startPosition = it.first()
                                                                val positionsToTest = ArrayDeque<Position<Pair<Int, Int>, QuadroSquareKind>>()
                                                                positionsToTest.add(startPosition)
                                                                val testedPositions = mutableSetOf<Position<Pair<Int, Int>, QuadroSquareKind>>()
                                                                while (positionsToTest.isNotEmpty()) {
                                                                    val nextPosition = positionsToTest.removeFirst()
                                                                    testedPositions.add(nextPosition)
                                                                    val adjacentPositions = when(nextPosition.kind) {
                                                                        QuadroSquareKind.Up -> listOf(
                                                                            Position(Pair(nextPosition.coordinates.first, nextPosition.coordinates.second+1), QuadroSquareKind.Down),
                                                                            Position(Pair(nextPosition.coordinates.first, nextPosition.coordinates.second), QuadroSquareKind.Left),
                                                                            Position(Pair(nextPosition.coordinates.first, nextPosition.coordinates.second), QuadroSquareKind.Right),
                                                                        )
                                                                        QuadroSquareKind.Down -> listOf(
                                                                            Position(Pair(nextPosition.coordinates.first, nextPosition.coordinates.second-1), QuadroSquareKind.Up),
                                                                            Position(Pair(nextPosition.coordinates.first, nextPosition.coordinates.second), QuadroSquareKind.Left),
                                                                            Position(Pair(nextPosition.coordinates.first, nextPosition.coordinates.second), QuadroSquareKind.Right),
                                                                        )
                                                                        QuadroSquareKind.Left -> listOf(
                                                                            Position(Pair(nextPosition.coordinates.first-1, nextPosition.coordinates.second), QuadroSquareKind.Right),
                                                                            Position(Pair(nextPosition.coordinates.first, nextPosition.coordinates.second), QuadroSquareKind.Up),
                                                                            Position(Pair(nextPosition.coordinates.first, nextPosition.coordinates.second), QuadroSquareKind.Down),
                                                                        )
                                                                        QuadroSquareKind.Right -> listOf(
                                                                            Position(Pair(nextPosition.coordinates.first+1, nextPosition.coordinates.second), QuadroSquareKind.Left),
                                                                            Position(Pair(nextPosition.coordinates.first, nextPosition.coordinates.second), QuadroSquareKind.Up),
                                                                            Position(Pair(nextPosition.coordinates.first, nextPosition.coordinates.second), QuadroSquareKind.Down),
                                                                        )
                                                                    }

                                                                    for (position in adjacentPositions) if (position !in testedPositions && position in it) positionsToTest.add(position)
                                                                }
                                                                testedPositions.size == it.size
                                                            }
                                                        },
                                                    )
                                                resultWindows.addAll(divisions.map { ResultWindow(ResultWindow.Data.QuadroSquare(it)) })
                                            }
                                            LatticeVariant.Triangular -> TriangleLattice {
                                                val divisions = triangularPositions
                                                    .map { Cell(it.key, emptySet<Nothing>()) }
                                                    .toSet()
                                                    .divideInParts(
                                                        numberOfParts = numberOfParts,
                                                        takeFormIf = {
                                                            !partsAreConnected || run {
                                                                val startPosition = it.first()
                                                                val positionsToTest = ArrayDeque<Position<Pair<Int, Int>, TriangleKind>>()
                                                                positionsToTest.add(startPosition)
                                                                val testedPositions = mutableSetOf<Position<Pair<Int, Int>, TriangleKind>>()
                                                                while (positionsToTest.isNotEmpty()) {
                                                                    val nextPosition = positionsToTest.removeFirst()
                                                                    testedPositions.add(nextPosition)
                                                                    val adjacentPositions = when(nextPosition.kind) {
                                                                        TriangleKind.Up -> listOf(
                                                                            Position(Pair(nextPosition.coordinates.first, nextPosition.coordinates.second-1), TriangleKind.Down),
                                                                            Position(Pair(nextPosition.coordinates.first-1, nextPosition.coordinates.second), TriangleKind.Down),
                                                                            Position(Pair(nextPosition.coordinates.first, nextPosition.coordinates.second), TriangleKind.Down),
                                                                        )
                                                                        TriangleKind.Down -> listOf(
                                                                            Position(Pair(nextPosition.coordinates.first, nextPosition.coordinates.second+1), TriangleKind.Up),
                                                                            Position(Pair(nextPosition.coordinates.first+1, nextPosition.coordinates.second), TriangleKind.Up),
                                                                            Position(Pair(nextPosition.coordinates.first, nextPosition.coordinates.second), TriangleKind.Up),
                                                                        )
                                                                    }

                                                                    for (position in adjacentPositions) if (position !in testedPositions && position in it) positionsToTest.add(position)
                                                                }
                                                                testedPositions.size == it.size
                                                            }
                                                        },
                                                    )
                                                resultWindows.addAll(divisions.map { ResultWindow(ResultWindow.Data.Triangular(it)) })
                                            }
                                        }
                                        showProcessing = false
                                    }
                                }
                            ) {
                                Text("Разрезать")
                            }
                        }
                    }
                }
            }
        }

        for (resultWindow in resultWindows) key(resultWindow) {
            resultWindow.Content(
                onCloseRequest = { resultWindows.remove(resultWindow) }
            )
        }
    }
}

class ResultWindow(
    private val data: Data,
) {
    @Composable
    fun Content(onCloseRequest: () -> Unit) {
        Window(
            title = "Result",
            icon = windowIcon,
            onCloseRequest = onCloseRequest,
        ) {
            data.Content()
        }
    }

    sealed interface Data {
        @Composable
        fun Content()

        data class Square(val parts: List<Set<Cell<Pair<Int, Int>, SquareKind, Nothing>>>): Data {
            @Composable
            override fun Content() {
                SquareLatticeCanvas.Content(
                    modifier = Modifier.fillMaxSize(),
                    onCellDraw = onCellDraw@{ position, tileSize ->
                        val partsIndex = parts.indexOfFirst { Cell(position) in it }
                        if (partsIndex == -1) return@onCellDraw

                        drawCircle(
                            color = colors[partsIndex],
                            center = Offset(0f, 0f),
                            radius = tileSize * 2,
                        )
                    }
                )
            }
        }
        data class QuadroSquare(val parts: List<Set<Cell<Pair<Int, Int>, QuadroSquareKind, Nothing>>>): Data {
            @Composable
            override fun Content() {
                QuadroSquareLatticeCanvas.Content(
                    modifier = Modifier.fillMaxSize(),
                    onCellDraw = onCellDraw@{ position, tileSize ->
                        val partsIndex = parts.indexOfFirst { Cell(position) in it }
                        if (partsIndex == -1) return@onCellDraw

                        drawCircle(
                            color = colors[partsIndex],
                            center = Offset(0f, 0f),
                            radius = tileSize * 2,
                        )
                    }
                )
            }
        }
        data class Triangular(val parts: List<Set<Cell<Pair<Int, Int>, TriangleKind, Nothing>>>): Data {
            @Composable
            override fun Content() {
                TriangleLatticeCanvas.Content(
                    modifier = Modifier.fillMaxSize(),
                    onCellDraw = onCellDraw@{ position, tileSize ->
                        val partsIndex = parts.indexOfFirst { Cell(position) in it }
                        if (partsIndex == -1) return@onCellDraw

                        drawCircle(
                            color = colors[partsIndex],
                            center = Offset(0f, 0f),
                            radius = tileSize * 2,
                        )
                    }
                )
            }
        }
    }
}