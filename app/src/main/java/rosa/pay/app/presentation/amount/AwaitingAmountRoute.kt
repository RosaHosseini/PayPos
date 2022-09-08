package rosa.pay.app.presentation.amount

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import rosa.pay.app.R
import rosa.pay.app.model.Money
import rosa.pay.app.presentation.TransactionViewModel
import rosa.pay.app.presentation.component.ActionButton
import rosa.pay.app.presentation.component.BottomSheetView
import rosa.pay.app.presentation.component.DisableBackButton
import rosa.pay.app.presentation.component.PayAppBar
import rosa.pay.app.ui.components.AnimatedSlideInVertically
import rosa.pay.app.ui.theme.Dimen
import rosa.pay.app.ui.theme.PayColor
import rosa.pay.app.ui.theme.Typography

@Composable
fun AwaitingAmountRoute(
    viewModel: TransactionViewModel,
    keyPadViewModel: KeyPadViewModel = hiltViewModel()
) {
    DisableBackButton()
    val offeredMoney = Money(keyPadViewModel.number.collectAsState().value)
    val isKeypadEnabled by keyPadViewModel.isKeypadEnabled.collectAsState()
    AwaitingAmountScreen(
        onSubmitAmount = viewModel::onSubmitAmount,
        isTransactionAmountPermitted = viewModel::isTransactionAmountPermitted,
        offeredMoney = offeredMoney,
        isKeypadButtonEnabled = { isKeypadEnabled or KeyPadViewModel.controlKeys.contains(it) },
        onKeyPadClick = keyPadViewModel::processInput
    )
}

@Composable
fun AwaitingAmountScreen(
    onSubmitAmount: (amount: Money) -> Unit,
    isTransactionAmountPermitted: (amount: Money) -> Boolean,
    offeredMoney: Money,
    isKeypadButtonEnabled: (Char) -> Boolean,
    onKeyPadClick: (Char) -> Unit
) {

    Surface(color = PayColor.GreyBackground) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            PayAppBar(
                title = stringResource(id = R.string.amount_title),
                textColor = PayColor.TextDark
            )
            Spacer(modifier = Modifier.height(Dimen.defaultMarginDouble))
            AmountTextView(offeredMoney)
            AnimatedSlideInVertically(isSlidIn = true) {
                BottomSheetView(backgroundColor = PayColor.BrandColor) {
                    KeyPadBoard(onKeyPadClick, isKeypadButtonEnabled)
                    Spacer(Modifier.height(Dimen.defaultMarginQuadruple))
                    SubmitButton(
                        { onSubmitAmount(offeredMoney) },
                        enabled = isTransactionAmountPermitted(offeredMoney)
                    )
                }
            }
        }
    }
}

@Composable
private fun AmountTextView(money: Money) {
    Text(
        text = money.toString(),
        style = Typography.h1,
        fontSize = if (money.amount.toString().count() > 5) 50.sp else 65.sp,
        color = PayColor.TextDark
    )
}

@Composable
private fun KeyPadBoard(onButtonClick: (Char) -> Unit, isKeypadButtonEnabled: (Char) -> Boolean) {
    KeyPadViewModel.offerKeypad.forEach { row ->
        Spacer(modifier = Modifier.height(Dimen.defaultMargin))
        Row {
            row.forEach { button ->
                Spacer(modifier = Modifier.width(Dimen.defaultMargin))
                IconButton(
                    onClick = { onButtonClick(button) },
                    enabled = isKeypadButtonEnabled(button),
                    modifier = Modifier
                        .size(Dimen.keyPadButtonSize)
                        .clip(CircleShape)
                ) {
                    Text(
                        text = button.toString(),
                        style = Typography.button,
                        fontSize = 32.sp,
                        color = PayColor.TextLight
                    )
                }
                Spacer(modifier = Modifier.width(Dimen.defaultMargin))
            }
        }
    }
}

@Composable
private fun SubmitButton(
    onClick: () -> Unit,
    enabled: Boolean
) {
    ActionButton(
        title = stringResource(R.string.submit),
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimen.defaultMarginDouble),
        enabled = enabled,
        color = PayColor.GreyBackground,
        textColor = PayColor.BrandColor
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAmountScreen() {
    AwaitingAmountScreen(
        onSubmitAmount = {},
        isTransactionAmountPermitted = { true },
        offeredMoney = Money(13000),
        isKeypadButtonEnabled = { true },
        onKeyPadClick = {}
    )
}