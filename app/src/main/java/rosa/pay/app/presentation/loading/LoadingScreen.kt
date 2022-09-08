package rosa.pay.app.presentation.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import rosa.pay.app.presentation.component.DisableBackButton
import rosa.pay.app.ui.components.DotsPulsing
import rosa.pay.app.ui.theme.PayColor

@Composable
fun LoadingScreen() {
    DisableBackButton()
    Box(Modifier.fillMaxSize()) {
        Box(Modifier.align(Alignment.Center)) {
            DotsPulsing(
                dotSize = 20.dp,
                delayUnit = 300,
                color = PayColor.BrandColor
            )
        }
    }
}