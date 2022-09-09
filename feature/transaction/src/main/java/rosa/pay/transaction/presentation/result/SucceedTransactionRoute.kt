package rosa.pay.transaction.presentation.result

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import rosa.pay.transaction.presentation.TransactionViewModel
import rosa.pay.transaction.presentation.component.DisableBackButton
import rosa.pay.app.ui.theme.PayColor
import rosa.pay.transaction.R
import rosa.pay.transaction.model.Money
import rosa.pay.transaction.model.TransactionUiState

@Composable
fun SucceedTransactionRoute(viewModel: TransactionViewModel) {
    DisableBackButton()
    val args by rememberSaveable {
        mutableStateOf(viewModel.transactionUiState.value as TransactionUiState.Success)
    }
    SucceedTransactionScreen(
        transferredMoney = args.transactionAmount,
        onBackToDashboard = viewModel::onReset
    )
}

@Composable
fun SucceedTransactionScreen(
    transferredMoney: Money,
    onBackToDashboard: () -> Unit
) {
    TransactionResultScreen(
        iconResource = R.drawable.success,
        description = stringResource(id = R.string.successful_transaction),
        transactionAmount = transferredMoney,
        actionButtonText = stringResource(id = R.string.back_to_dashboard),
        primaryColor = PayColor.BrandColor,
        onActionButtonClick = onBackToDashboard
    )
}

@Preview(showBackground = true)
@Composable
private fun SucceedTransactionPreview() {
    SucceedTransactionScreen(transferredMoney = Money(13000)) { }
}