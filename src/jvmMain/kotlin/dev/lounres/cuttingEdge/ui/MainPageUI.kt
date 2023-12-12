package dev.lounres.cuttingEdge.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import dev.lounres.cuttingEdge.components.MainPageComponent
import dev.lounres.cuttingEdge.components.fake.FakeMainPageComponent
import dev.lounres.cuttingEdge.ui.components.HorizontalDivider
import dev.lounres.cuttingEdge.ui.components.VerticalDivider


@Preview
@Composable
fun MainPageUIPreview() {
    MainPageUI(component = FakeMainPageComponent())
}

@Composable
fun MainPageUI(
    component: MainPageComponent,
) {
    component.launchPartitionsCollection(rememberCoroutineScope())
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth().weight(1f)) {
            MainPageLatticeUI(
                modifier = Modifier.weight(1f),
                component = component.mainPageLatticeComponent
            )

            VerticalDivider()

            MainPageControlPaneUI(
                component = component.mainPageControlPaneComponent
            )
        }

        HorizontalDivider()

        BottomCardPreviewListUI(component = component.bottomCardPreviewListComponent)
    }
}