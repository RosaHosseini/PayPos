package rosa.pay.app.ui.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

@Composable
fun AnimatedSlideInVertically(
    isSlidIn: Boolean = true,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    var isVisible: Boolean by remember { mutableStateOf(isSlidIn.not()) }
    LaunchedEffect(isSlidIn) {
        isVisible = isSlidIn
    }
    AnimatedVisibility(
        visible = isVisible,
        enter = slideIn(initialOffset = { fullSize ->
            IntOffset(
                x = 0,
                y = fullSize.height
            )
        }),
        exit = slideOut(
            targetOffset = { fullSize: IntSize ->
                IntOffset(
                    x = 0,
                    y = fullSize.height
                )
            }
        )
    ) {
        content()
    }
}