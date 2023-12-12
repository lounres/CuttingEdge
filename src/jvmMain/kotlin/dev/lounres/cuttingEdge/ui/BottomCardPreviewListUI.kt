package dev.lounres.cuttingEdge.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import dev.lounres.composeLatticeCanvas.TriangleLatticeCanvas
import dev.lounres.cuttingEdge.components.BottomCardPreviewListComponent
import dev.lounres.cuttingEdge.components.fake.FakeBottomCardPreviewListComponent
import dev.lounres.cuttingEdge.ui.components.PartitionPreviewComponent
import dev.lounres.cuttingEdge.ui.components.VerticalDivider
import dev.lounres.kone.misc.lattices.Cell
import dev.lounres.kone.misc.lattices.TriangleKind
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@Preview
@Composable
fun BottomCardPreviewListUIPreview() {
    BottomCardPreviewListUI(
        component = FakeBottomCardPreviewListComponent(
            listOf(
                PartitionPreviewComponent(
                    latticeCanvas = TriangleLatticeCanvas,
                    parts = listOf(
                        setOf(
                            Cell(Pair(0, 0), TriangleKind.Up, emptySet()),
                            Cell(Pair(-1, 0), TriangleKind.Up, emptySet()),
                            Cell(Pair(-1, 0), TriangleKind.Down, emptySet()),
                            Cell(Pair(0, -1), TriangleKind.Up, emptySet()),
                            Cell(Pair(0, -1), TriangleKind.Down, emptySet()),
                            Cell(Pair(-1, -1), TriangleKind.Down, emptySet()),
                        )
                    ),
                    figureCenter = Offset(0f, 0f),
                )
            )
        )
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BottomCardPreviewListUI(
    component: BottomCardPreviewListComponent,
) {
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
                onClick = component::onClearPartitionCardPreviewComponents,
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
                for ((index, previewComponent) in component.partitionCardPreviewComponents.withIndex()) item(key = previewComponent) {
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
                            previewComponent.Content(
                                modifier = Modifier.fillMaxSize()
                            )
                            FilledIconButton(
                                modifier = Modifier.align(Alignment.TopStart),
                                onClick = { component.onAddPartitionWindowPreviewComponent(previewComponent.copy()) },
                            ) {
                                Icon(
                                    painter = painterResource("icons/open_in_new.png"),
                                    contentDescription = null,
                                )
                            }
                            FilledIconButton(
                                modifier = Modifier.align(Alignment.TopEnd),
                                onClick = { component.onRemovePartitionCardPreviewComponent(index) },
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