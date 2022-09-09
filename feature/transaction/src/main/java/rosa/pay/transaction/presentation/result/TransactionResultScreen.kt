package rosa.pay.transaction.presentation.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import rosa.pay.app.ui.components.ActionButton
import rosa.pay.transaction.presentation.component.SlideInCard
import rosa.pay.app.ui.widget.BlurredBackground
import rosa.pay.app.ui.theme.Dimen
import rosa.pay.app.ui.theme.PayColor
import rosa.pay.app.ui.theme.Typography
import rosa.pay.transaction.R
import rosa.pay.transaction.model.Money

@Composable
fun TransactionResultScreen(
    iconResource: Int,
    description: String,
    transactionAmount: Money,
    actionButtonText: String,
    primaryColor: Color,
    onActionButtonClick: () -> Unit
) {
    BlurredBackground(color = primaryColor) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(
                    start = Dimen.defaultMarginDouble,
                    end = Dimen.defaultMarginDouble,
                    top = Dimen.defaultMarginDouble,
                    bottom = Dimen.defaultBottomMargin
                )
        ) {
            SlideInCard(
                modifier = Modifier.align(Alignment.Center),
                primaryColor = primaryColor,
                secondaryColor = PayColor.Silver,
                iconResource = iconResource,
                topContent = { TopContent(transactionAmount, description) },
                bottomContent = { BottomContent() }
            )
            ActionButton(
                title = actionButtonText,
                onClick = onActionButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                color = primaryColor
            )
        }
    }
}

@Composable
private fun TopContent(transferredMoney: Money, description: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = description,
        color = PayColor.TextLight,
        style = Typography.body2,
        textAlign = TextAlign.Center
    )
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimen.defaultMargin),
        text = transferredMoney.toString(),
        color = PayColor.TextLight,
        style = Typography.h1,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun ColumnScope.BottomContent() {
    Text(
        modifier = Modifier
            .padding(
                vertical = Dimen.defaultMarginDouble,
                horizontal = Dimen.defaultMargin
            )
            .fillMaxWidth(),
        text = stringResource(id = R.string.payment_using_pay),
        color = PayColor.TextDark,
        style = Typography.body2,
        textAlign = TextAlign.Center,
    )
    Image(
        modifier = Modifier
            .padding(bottom = Dimen.defaultMarginDouble)
            .sizeIn(maxWidth = 100.dp)
            .align(Alignment.CenterHorizontally),
        painter = painterResource(id = R.drawable.pay),
        contentDescription = ""
    )
}
