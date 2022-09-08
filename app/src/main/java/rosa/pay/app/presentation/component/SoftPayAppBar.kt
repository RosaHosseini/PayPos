package rosa.pay.app.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import rosa.pay.app.ui.theme.PayColor
import rosa.pay.app.ui.theme.Typography

@Composable
fun PayAppBar(
    title: String,
    modifier: Modifier = Modifier,
    textColor: Color = PayColor.TextDark
) {
    TopAppBar(
        modifier,
        backgroundColor = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = title,
                style = Typography.h3,
                textAlign = TextAlign.Center,
                color = textColor,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}