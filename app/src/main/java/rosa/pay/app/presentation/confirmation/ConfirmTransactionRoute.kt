package rosa.pay.app.presentation.confirmation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import rosa.pay.sdk.Store
import rosa.pay.app.R
import rosa.pay.app.model.Money
import rosa.pay.app.model.TransactionUiState
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
fun ConfirmTransactionRoute(viewModel: TransactionViewModel) {
    DisableBackButton()
    val args by rememberSaveable {
        mutableStateOf(
            viewModel.transactionUiState.value as TransactionUiState.AwaitingConfirmation
        )
    }
    ConfirmTransactionScreen(
        transferredMoney = args.transactionAmount,
        store = args.store,
        onConfirm = viewModel::onConfirmTransaction
    )
}

@Composable
fun ConfirmTransactionScreen(
    transferredMoney: Money,
    store: Store,
    onConfirm: (Boolean) -> Unit
) {
    Surface(color = PayColor.GreyBackground) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            val (title, icon, name, address, postalCode, amount, tip, bottomSheet) = createRefs()
            val chainRef = createVerticalChain(
                icon, name, address, postalCode,
                chainStyle = ChainStyle.Packed
            )
            constrain(chainRef) {
                top.linkTo(title.bottom)
                bottom.linkTo(amount.top)
            }
            PayAppBar(
                title = stringResource(id = R.string.confirm_transform_title),
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.icon_wallet),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(maxHeight = 110.dp, minHeight = 90.dp)
                    .padding(Dimen.defaultMargin)
                    .constrainAs(icon) {
                        bottom.linkTo(name.top)
                    }
            )
            Text(
                text = store.name,
                style = Typography.h2,
                color = PayColor.TextDark,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = Dimen.defaultMarginHalf,
                        horizontal = Dimen.defaultMarginDouble
                    )
                    .constrainAs(name) {
                        top.linkTo(icon.bottom)
                        bottom.linkTo(address.top)
                    }
            )
            Text(
                text = store.address,
                style = Typography.body1,
                color = PayColor.TextDark,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = Dimen.defaultMarginHalf,
                        horizontal = Dimen.defaultMarginDouble
                    )
                    .constrainAs(address) {
                        top.linkTo(name.bottom)
                        bottom.linkTo(postalCode.top)
                    }
            )
            Text(
                text = store.postalCode,
                style = Typography.body1,
                color = PayColor.TextDark,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = Dimen.defaultMarginHalf,
                        horizontal = Dimen.defaultMarginDouble
                    )
                    .constrainAs(postalCode) {
                        top.linkTo(address.bottom)
                    }
            )
            Text(
                text = transferredMoney.toString(),
                style = Typography.h1,
                color = PayColor.TextDark,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimen.defaultMarginDouble)
                    .constrainAs(amount) {
                        top.linkTo(postalCode.bottom)
                        bottom.linkTo(tip.top)
                    }
            )
            TipBox(
                Modifier
                    .padding(
                        bottom = Dimen.defaultMarginTriple,
                        start = Dimen.defaultMarginDouble,
                        end = Dimen.defaultMarginDouble,
                    )
                    .constrainAs(tip) {
                        bottom.linkTo(bottomSheet.top)
                    }
            )
            Box(
                modifier = Modifier
                    .constrainAs(bottomSheet) {
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                AnimatedSlideInVertically(isSlidIn = true) {
                    ConfirmationBottomSheet(onConfirm)
                }
            }
        }
    }
}

@Composable
private fun TipBox(modifier: Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimen.defaultCornerRadius),
        backgroundColor = PayColor.LightGreen3,
        elevation = Dimen.defaultElevation
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.defaultMarginDouble)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_info),
                contentDescription = "",
                alignment = Alignment.TopStart,
                modifier = Modifier
                    .padding(top = Dimen.defaultMarginHalf, end = Dimen.defaultMargin)
                    .size(14.dp)
            )
            Text(
                text = stringResource(id = R.string.free_transfer),
                style = Typography.body1,
                color = PayColor.TextLight
            )
        }
    }
}

@Composable
private fun ConfirmationBottomSheet(onConfirm: (Boolean) -> Unit) {
    BottomSheetView {
        Column(
            Modifier.padding(
                start = Dimen.defaultMarginDouble,
                end = Dimen.defaultMarginDouble,
                top = Dimen.defaultMargin
            )
        ) {
            Text(
                text = stringResource(id = R.string.confirm_transform_desc),
                style = Typography.h3,
                color = PayColor.TextDark,
                modifier = Modifier.padding(
                    bottom = Dimen.defaultMarginTriple,
                    start = Dimen.defaultMargin,
                    end = Dimen.defaultMargin
                )
            )
            Row {
                ActionButton(
                    title = stringResource(id = R.string.cancel),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(end = Dimen.defaultMargin),
                    onClick = { onConfirm(false) }
                )
                ActionButton(
                    title = stringResource(id = R.string.confirm_transform),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = Dimen.defaultMargin),
                    onClick = { onConfirm(true) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ConfirmTransactionPreview() {
    ConfirmTransactionScreen(
        Money(13000),
        Store(
            name = "Big Kahuna Burger",
            address = "Pulp fiction st. 28, 94",
            postalCode = "2100"
        )
    ) { }
}