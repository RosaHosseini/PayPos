package rosa.pay.app.presentation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import rosa.pay.app.ui.theme.Dimen
import rosa.pay.app.ui.theme.PayColor
import rosa.pay.app.ui.theme.Typography

@Composable
fun ActionButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = PayColor.BrandColor,
    textColor: Color = PayColor.TextLight,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier
            .clip(RoundedCornerShape(Dimen.defaultCornerRadius))
            .alpha(if (enabled) 1f else 0.8f),
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        onClick = onClick,
        contentPadding = PaddingValues(Dimen.defaultMarginDouble),
        elevation = ButtonDefaults.elevation(defaultElevation = Dimen.defaultElevation),
        enabled = enabled
    ) {
        Text(
            text = title,
            color = textColor,
            style = Typography.button
        )
    }
}