package rosa.pay.transaction.presentation.component

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
fun DisableBackButton() {
    BackHandler(true) {}
}