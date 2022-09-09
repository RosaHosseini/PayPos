package rosa.pay.app.ui.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BlurredBackground(
    color: Color,
    modifier: Modifier = Modifier.fillMaxSize(),
    content: @Composable () -> Unit
) {
    Box {
        Box(
            modifier.background(
                Brush.linearGradient(
                    colors = listOf(
                        color,
                        Color.LightGray
                    )
                )
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.2f))
                    .blur(radius = 200.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                color,
                                Color.Transparent,
                            ),
                            center = Offset(x = 0.4f, y = 0.2f),
                            radius = size.minDimension

                        ),
                        center = Offset(x = 0.4f, y = 0.2f),
                        radius = size.minDimension
                    )
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.LightGray.copy(0.8f),
                                Color.Transparent,
                            ),
                            center = Offset(x = size.width - 0.8f, y = 0.5f),
                            radius = size.minDimension / 2
                        ),
                        center = Offset(x = size.width - 0.8f, y = 0.5f),
                        radius = size.minDimension / 2
                    )
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                color,
                                Color.Transparent,
                            ),
                            center = Offset(x = 0.4f, y = size.height),
                            radius = size.minDimension / 4
                        ),
                        center = Offset(x = 0.4f, y = size.height),
                        radius = size.minDimension / 4
                    )

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.LightGray,
                                Color.Transparent,
                            ),
                            center = Offset(x = size.width - 0.8f, y = size.height)
                        ),
                        center = Offset(x = size.width - 0.8f, y = size.height),
                    )
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                color,
                                Color.Transparent,
                            ),
                            center = Offset(x = size.width - 0.8f, y = size.height),
                            radius = size.minDimension / 4
                        ),
                        center = Offset(x = size.width - 0.8f, y = size.height),
                        radius = size.minDimension / 4
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(0.2f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(1f)
                    .blur(
                        radius = 1000.dp,
                        edgeTreatment = BlurredEdgeTreatment.Unbounded
                    )
                    .background(
                        Brush.radialGradient(
                            listOf(
                                Color(0x12FFFFFF),
                                Color(0xDFFFFFF),
                                Color(0x9FFFFFFF)

                            ),
                            radius = 2200f,
                            center = Offset.Infinite
                        )
                    )
            )
        }
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            content()
        }
    }
}