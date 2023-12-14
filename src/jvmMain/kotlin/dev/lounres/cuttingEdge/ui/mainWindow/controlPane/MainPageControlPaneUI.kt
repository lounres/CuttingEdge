package dev.lounres.cuttingEdge.ui.mainWindow.controlPane

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import dev.lounres.cuttingEdge.components.mainWindow.controlPane.MainPageControlPaneComponent
import dev.lounres.cuttingEdge.components.mainWindow.controlPane.FakeMainPageControlPaneComponent
import dev.lounres.cuttingEdge.possibleNumbersOfParts
import kotlinx.coroutines.Dispatchers


@Preview
@Composable
fun MainPageControlPaneUIPreview() {
    MainPageControlPaneUI(
        component = FakeMainPageControlPaneComponent()
    )
}

@Composable
fun MainPageControlPaneUI(
    component: MainPageControlPaneComponent,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(IntrinsicSize.Max),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val latticeVariantIndex by component.latticeVariantIndex.subscribeAsState()
        val latticeVariant by remember { derivedStateOf { component.latticeVariants[latticeVariantIndex] } }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            val showProcessing by component.showProcessing.subscribeAsState()
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
                        onClick = { component.onLatticeVariantSelected(0) },
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
                        onClick = { component.onLatticeVariantSelected(1) },
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
                        onClick = { component.onLatticeVariantSelected(2) },
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
                        checked = component.partsAreConnected.subscribeAsState().value,
                        onCheckedChange = component::onPartsAreConnectedChange,
                    )
                }

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
                                text = component.numberOfParts.subscribeAsState().value.toString(),
                            )
                        }
                        DropdownMenu(
                            expanded = showPossibleNumbersOfParts,
                            onDismissRequest = { showPossibleNumbersOfParts = false }
                        ) {
                            possibleNumbersOfParts.forEach { numberOfParts ->
                                DropdownMenuItem(
                                    text = {
                                        Text(numberOfParts.toString())
                                    },
                                    onClick = {
                                        component.onNumberOfPartsChange(newNumberOfParts = numberOfParts)
                                        showPossibleNumbersOfParts = false
                                    }
                                )
                            }
                        }
                    }
                }

                val partitionsComputationCoroutineScope = rememberCoroutineScope { Dispatchers.Default }
                Button(
                    onClick = { component.onComputePartitions(partitionsComputationCoroutineScope) },
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