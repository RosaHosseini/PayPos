package rosa.pay.app.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import rosa.pay.app.ui.theme.Dimen
import rosa.pay.app.ui.theme.PayColor

@Composable
fun BottomSheetView(
    modifier: Modifier = Modifier,
    backgroundColor: Color = PayColor.LightBackground,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = Dimen.bottomSheetCornerRadius,
                    topEnd = Dimen.bottomSheetCornerRadius
                )
            ),
        backgroundColor = backgroundColor,
        elevation = Dimen.defaultElevation
    ) {
        Column(
            modifier = Modifier.padding(bottom = Dimen.defaultBottomMargin),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Divider(
                color = PayColor.GreyBackground,
                modifier = Modifier
                    .padding(vertical = Dimen.defaultMarginDouble)
                    .height(5.dp)
                    .width(80.dp)
                    .align(CenterHorizontally)
            )
            content()
        }
    }
}