package dev.lounres.cuttingEdge.ui.mainWindow.lattice

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import dev.lounres.cuttingEdge.components.mainWindow.lattice.MainPageLatticeComponent
import dev.lounres.cuttingEdge.components.mainWindow.lattice.FakeMainPageLatticeComponent


@Preview
@Composable
fun MainPageLatticeUIPreview() {
    MainPageLatticeUI(
        modifier = Modifier.fillMaxSize(),
        component = FakeMainPageLatticeComponent(),
    )
}

@Composable
fun MainPageLatticeUI(
    component: MainPageLatticeComponent,
    modifier: Modifier = Modifier,
) {
    val latticeVariantIndex by component.latticeVariantIndex.subscribeAsState()
    val latticeVariant by remember { derivedStateOf { component.latticeVariants[latticeVariantIndex] } }

    key(latticeVariant) {
        latticeVariant.Content(modifier = modifier.fillMaxHeight())
    }
}