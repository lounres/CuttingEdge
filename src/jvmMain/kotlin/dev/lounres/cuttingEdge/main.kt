package dev.lounres.cuttingEdge

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.lounres.composeLatticeCanvas.QuadroSquareLatticeCanvas
import dev.lounres.composeLatticeCanvas.SquareLatticeCanvas
import dev.lounres.composeLatticeCanvas.TriangleLatticeCanvas
import dev.lounres.kone.misc.lattices.QuadroSquareLattice
import dev.lounres.kone.misc.lattices.SquareLattice
import dev.lounres.kone.misc.lattices.TriangleLattice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


fun main() {
    application {
        val partitionPreviewComponents: MutableList<PartitionPreviewComponent<*, *>> = remember { mutableStateListOf() }

        var isOpen by remember { mutableStateOf(true) }
        if (isOpen) Window(
            title = "CuttingEdge — Canvas",
            icon = windowIcon,
            onCloseRequest = { isOpen = !isOpen },
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                val latticeVariants: List<LatticeCanvasComponent<*, *, *>> = remember {
                    listOf(
                        createLatticeCanvasComponent(lattice = SquareLattice, latticeCanvas = SquareLatticeCanvas) {
                            if (this.isNotEmpty())
                                Offset(
                                    (this.maxOf { it.coordinates.first + 1 } + this.minOf { it.coordinates.first }) / 2f,
                                    (this.maxOf { it.coordinates.second + 1 } + this.minOf { it.coordinates.second }) / 2f
                                )
                            else Offset(0f, 0f)
                        },
                        createLatticeCanvasComponent(lattice = QuadroSquareLattice, latticeCanvas = QuadroSquareLatticeCanvas) {
                            if (this.isNotEmpty())
                            // TODO: Это стоит переписать корректнее.
                                Offset(
                                    (this.maxOf { it.coordinates.first + 1 } + this.minOf { it.coordinates.first }) / 2f,
                                    (this.maxOf { it.coordinates.second + 1 } + this.minOf { it.coordinates.second }) / 2f
                                )
                            else Offset(0f, 0f)
                        },
                        createLatticeCanvasComponent(lattice = TriangleLattice, latticeCanvas = TriangleLatticeCanvas) {
                            if (this.isNotEmpty())
                            // TODO: Это стоит переписать корректнее.
                                Offset(
                                    (this.maxOf { it.coordinates.first + 1 } + this.minOf { it.coordinates.first }) / 2f,
                                    (this.maxOf { it.coordinates.second + 1 } + this.minOf { it.coordinates.second }) / 2f
                                )
                            else Offset(0f, 0f)
                        },
                    )
                }
                var latticeVariantIndex by remember { mutableIntStateOf(0) }
                val latticeVariant by remember { derivedStateOf { latticeVariants[latticeVariantIndex] } }
                var partsAreConnected by remember { mutableStateOf(true) }

                key(latticeVariant) {
                    latticeVariant.Content(modifier = Modifier.fillMaxHeight().weight(1f))
                }

                Surface(
                    modifier = Modifier.fillMaxHeight().width(IntrinsicSize.Max)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        var showProcessing by remember { mutableStateOf(false) }
                        if (showProcessing) LinearProgressIndicator(
                            modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter)
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter).padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                val cornerRadius = 16.dp

                                OutlinedButton(
                                    modifier = Modifier.offset(2.dp, 0.dp),
                                    enabled = latticeVariantIndex != 0,
                                    shape = RoundedCornerShape(
                                        topStart = cornerRadius,
                                        topEnd = 0.dp,
                                        bottomStart = cornerRadius,
                                        bottomEnd = 0.dp
                                    ),
                                    onClick = { latticeVariantIndex = 0 },
                                ) {
                                    Text("Квадратная")
                                }

                                OutlinedButton(
                                    enabled = latticeVariantIndex != 1,
                                    shape = RoundedCornerShape(
                                        topStart = 0.dp,
                                        topEnd = 0.dp,
                                        bottomStart = 0.dp,
                                        bottomEnd = 0.dp
                                    ),
                                    onClick = { latticeVariantIndex = 1 },
                                ) {
                                    Text("Четверть-квадратная")
                                }

                                OutlinedButton(
                                    modifier = Modifier.offset((-2).dp, 0.dp),
                                    enabled = latticeVariantIndex != 2,
                                    shape = RoundedCornerShape(
                                        topStart = 0.dp,
                                        topEnd = cornerRadius,
                                        bottomStart = 0.dp,
                                        bottomEnd = cornerRadius
                                    ),
                                    onClick = { latticeVariantIndex = 2 },
                                ) {
                                    Text("Треугольная")
                                }
                            }

                            Row(
                                horizontalArrangement = Arrangement.Center,
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
                            Row {
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
                                onClick = {
                                    showProcessing = true
                                    coroutineScope.launch {
                                        with(latticeVariant) {
                                            partitionPreviewComponents.addPartitions(
                                                numberOfParts = numberOfParts,
                                                partsAreConnected = partsAreConnected
                                            )
                                        }
                                        showProcessing = false
                                    }
                                }
                            ) {
                                Text("Разрезать")
                            }
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).padding(10.dp),
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = "Количество отмеченных клеток: ${latticeVariant.positions.size}",
                            )
                        }
                    }
                }
            }
        }

        for (partitionPreviewComponent in partitionPreviewComponents) key(partitionPreviewComponent) {
            Window(
                title = "CuttingEdge — Result",
                icon = windowIcon,
                onCloseRequest = {
                     partitionPreviewComponents.remove(partitionPreviewComponent)
                },
            ) {
                partitionPreviewComponent.Content(Modifier.fillMaxSize())
            }
        }
    }
}