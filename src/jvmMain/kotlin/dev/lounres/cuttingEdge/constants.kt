package dev.lounres.cuttingEdge

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
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

val DEFAULT_TILE_SIZE = 64.dp
const val ZOOM_MIN = 0.1f
const val ZOOM_MAX = 10f