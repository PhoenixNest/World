package io.core.ui.shape

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import io.core.ui.theme.RelicAppTheme
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

/**
 * [Custom Shapes in Jetpack Compose](https://engineering.deptagency.com/custom-shapes-in-jetpack-compose)
 * */
class TriangleShape(

    private val roundRadius: Float
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = Path().apply {
                val CR = size.width
                val AR = size.height / 2

                val DAE = atan(CR / AR)
                val DAQ = PI.toFloat() / 2 - DAE
                val DAM = DAE / 2
                val AD = roundRadius / tan(DAM)
                val DQ = AD * sin(DAQ)
                val AQ = AD * cos(DAQ)

                // move to point D
                moveTo(AQ, DQ)

                val GCN = (PI.toFloat() - 2 * DAE) / 2
                val CG = roundRadius / tan(GCN)
                val GT = CG * sin(GCN)
                val CT = CG * cos(GCN)

                // line to point G
                lineTo(CR - CT, AR - GT)

                val CN = roundRadius / sin(GCN)
                val CNG = PI.toFloat() / 2 - GCN
                // right arc
                arcToRad(Rect(Offset(CR - CN, AR), roundRadius), -CNG, 2 * CNG, false)

                val AB = size.height

                // line to point K
                lineTo(AQ, AB - DQ)

                val OBV = DAM + DAQ
                val BO = roundRadius / sin(DAM)
                val BV = BO * cos(OBV)
                val OV = BO * sin(OBV)

                val BOV = PI.toFloat() - OBV
                val BOK = PI.toFloat() - DAM
                val KOV = BOK - BOV
                val KOU = PI.toFloat() / 2 - KOV
                val JOK = PI.toFloat() - DAE

                // bottom left arc
                arcToRad(Rect(Offset(BV, AB - OV), roundRadius), KOU, JOK, false)

                // line to point E
                lineTo(0f, AD)

                // top left arc
                arcToRad(Rect(Offset(BV, OV), roundRadius), PI.toFloat(), JOK, false)
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun TriangleShapePreview() {
    RelicAppTheme {
        Box(
            modifier = Modifier.size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = TriangleShape(48F),
                onClick = {},
                color = Color.DarkGray,
                modifier = Modifier.size(240.dp)
            ) {
                //
            }
        }
    }
}