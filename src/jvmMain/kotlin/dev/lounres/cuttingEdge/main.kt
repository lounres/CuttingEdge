package dev.lounres.cuttingEdge

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
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
import dev.lounres.kone.misc.lattices.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import kotlin.io.path.bufferedWriter
import kotlin.io.path.createFile
import kotlin.io.path.createParentDirectories
import kotlin.io.path.notExists


@OptIn(ExperimentalResourceApi::class)
fun main() {
    historyFilePath.createParentDirectories()
    if (historyFilePath.notExists()) {
        historyFilePath.createFile()
        historyFilePath.bufferedWriter().use { it.write("[]") }
    }

    application {
        val partitionWindowPreviewComponents: MutableList<PartitionPreviewComponent<*, *>> = remember { mutableStateListOf() }

        var isOpen by remember { mutableStateOf(true) }
        if (isOpen) {
            val partitionCardPreviewComponents: MutableList<PartitionPreviewComponent<*, *>> =
                remember { mutableStateListOf() }
            val partitionChannel = remember { Channel<PartitionPreviewComponent<*, *>>(Channel.UNLIMITED) }
            val partitionsInputCoroutineScope = rememberCoroutineScope()
            partitionsInputCoroutineScope.launch {
                while (true) partitionCardPreviewComponents.add(partitionChannel.receive())
            }

            Window(
                title = "CuttingEdge — Canvas",
                icon = windowIcon,
                onCloseRequest = { isOpen = !isOpen },
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(modifier = Modifier.fillMaxWidth().weight(1f)) {
                        val latticeVariants: List<LatticeCanvasComponent<*, *, *>> = remember {
                            listOf(
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
                        }
                        var latticeVariantIndex by remember { mutableIntStateOf(0) }
                        val latticeVariant by remember { derivedStateOf { latticeVariants[latticeVariantIndex] } }

                        key(latticeVariant) {
                            latticeVariant.Content(modifier = Modifier.fillMaxHeight().weight(1f))
                        }

                        VerticalDivider()

                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(IntrinsicSize.Max),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                var showProcessing by remember { mutableStateOf(false) }
                                Column(
                                    modifier = Modifier.fillMaxWidth().height(4.dp)
                                ) {
                                    if (showProcessing) LinearProgressIndicator(
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Column(
                                    modifier = Modifier.fillMaxWidth().padding(10.dp),
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

                                    var partsAreConnected by remember { mutableStateOf(true) }
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
                                        Box(
                                            modifier = Modifier.align(Alignment.CenterVertically),
                                        ) {
                                            var showPossibleNumbersOfParts by remember { mutableStateOf(false) }
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
                                                        text = {
                                                            Text(number.toString())
                                                        },
                                                        onClick = {
                                                            numberOfParts = number
                                                            showPossibleNumbersOfParts = false
                                                        }
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    val partitionsComputationCoroutineScope =
                                        rememberCoroutineScope { Dispatchers.Default }
                                    Button(
                                        onClick = {
                                            showProcessing = true
                                            partitionsComputationCoroutineScope.launch {
                                                with(latticeVariant) {
                                                    partitionChannel.addPartitions(
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
                            }

                            Column(
                                modifier = Modifier.fillMaxWidth().padding(10.dp),
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    text = "Количество отмеченных позиций: ${latticeVariant.positions.size}",
                                )
                            }
                        }
                    }

                    HorizontalDivider()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Max)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(IntrinsicSize.Max),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconButton(
                                modifier = Modifier.padding(2.dp),
                                onClick = { /* TODO */ },
                            ) {
                                Icon(
                                    painter = painterResource("icons/save.png"),
                                    contentDescription = null,
                                )
                            }
                            IconButton(
                                modifier = Modifier.padding(2.dp),
                                onClick = { partitionCardPreviewComponents.clear() },
                            ) {
                                Icon(
                                    painter = painterResource("icons/delete.png"),
                                    contentDescription = null,
                                )
                            }
                        }
                        VerticalDivider(
                            modifier = Modifier.padding(vertical = 10.dp),
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                        ) {
                            val lazyListState = rememberLazyListState(0)
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                state = lazyListState,
                            ) {
                                for ((index, component) in partitionCardPreviewComponents.withIndex()) item(key = component) {
                                    if (index != 0) Spacer(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .width(10.dp)
                                    )
                                    ElevatedCard(
                                        modifier = Modifier
                                            .size(width = 300.dp, height = 300.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier.fillMaxSize()
                                        ) {
                                            component.Content(
                                                modifier = Modifier.fillMaxSize()
                                            )
                                            FilledIconButton(
                                                modifier = Modifier.align(Alignment.TopStart),
                                                onClick = { partitionWindowPreviewComponents.add(component.copy()) },
                                            ) {
                                                Icon(
                                                    painter = painterResource("icons/open_in_new.png"),
                                                    contentDescription = null,
                                                )
                                            }
                                            FilledIconButton(
                                                modifier = Modifier.align(Alignment.TopEnd),
                                                onClick = { partitionCardPreviewComponents.removeAt(index) },
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Close,
                                                    contentDescription = null,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            HorizontalScrollbar(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(10.dp),
                                adapter = rememberScrollbarAdapter(lazyListState),
                            )
                        }
                    }
                }
            }
        }

        for (component in partitionWindowPreviewComponents) key(component) {
            Window(
                title = "CuttingEdge — Result",
                icon = windowIcon,
                onCloseRequest = {
                    partitionWindowPreviewComponents.remove(component)
                },
            ) {
                component.Content(Modifier.fillMaxSize())
            }
        }
    }
}