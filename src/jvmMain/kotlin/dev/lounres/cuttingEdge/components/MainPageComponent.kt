package dev.lounres.cuttingEdge.components

import kotlinx.coroutines.CoroutineScope


interface MainPageComponent {
    val mainPageLatticeComponent: MainPageLatticeComponent
    val mainPageControlPaneComponent: MainPageControlPaneComponent
    val bottomCardPreviewListComponent: BottomCardPreviewListComponent

    fun launchPartitionsCollection(coroutineScope: CoroutineScope)
}